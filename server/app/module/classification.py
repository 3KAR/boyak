from keras import models
from keras.preprocessing.image import ImageDataGenerator
import pickle,numpy as np
# gpu 가동
import tensorflow as tf
gpus = tf.config.experimental.list_physical_devices('GPU')
for gpu in gpus:
    tf.config.experimental.set_memory_growth(gpu, True)

def cate_class():
    model = models.load_model('/home/team/myenv/app/models/model_cate.h5')

    test_datagen = ImageDataGenerator(rescale=1./255)

    test_generator = test_datagen.flow_from_directory(
            './app/images',
            target_size=(80, 80),
            batch_size=10,
            class_mode='categorical')

    with open('./app/models/cate.pickle','rb') as f:
        cate_dict = pickle.load(f)
    prediction = model.predict_generator(test_generator)

    for name,label in cate_dict.items():
        if label == np.argmax(prediction):
            cate = name
    return cate

def drug_class(cate):
    model = models.load_model(f'./app/models/model_{cate}.h5')

    test_datagen = ImageDataGenerator(rescale=1./255)

    test_generator = test_datagen.flow_from_directory(
            './app/images',
            target_size=(80, 80),
            batch_size=10,
            class_mode='categorical')


    prediction = model.predict_generator(test_generator)
    with open(f'./app/models/{cate}.pickle','rb') as f:
        cate_dict = pickle.load(f)
    for name,label in cate_dict.items():
        if label == np.argmax(prediction):
            item_seq = name
            persent = prediction[0][label] * 100
    
    return item_seq,persent




