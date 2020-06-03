from app.module import dbModule, classification, image_detection
from keras import models
import base64
from flask import Blueprint, request, render_template, flash, redirect, url_for
from flask import current_app as app

result = Blueprint('result', __name__, url_prefix='/result')
db_class= dbModule.Database()

@result.route('/', methods=['GET'])
def index():
    
    image_detection.image_preprocessing('./app/images/','./app/images/classi_image/')
    model = models.load_model('/home/team/myenv/app/models/model_cate.h5')
    # cate = classification.cate_class()
    # itemSeq, percentage = classification.drug_class(cate)
    itemSeq = request.args.get('item_seq')
    if itemSeq is None:
        itemSeq = 199600089
    img_sql = f'SELECT item_img FROM ITEM_IMG WHERE item_seq = {itemSeq}'
    tmp = db_class.executeOne(img_sql)
    image = base64.b64encode(tmp['item_img']).decode("utf-8")

    name_sql = f'''
                SELECT 
                    item_name
                FROM 
                    ITEM_INFO
                WHERE 
                    item_seq = {itemSeq}
                '''
    name = db_class.executeOne(name_sql)
    basic_sql = f'''
                SELECT 
                    ITEM_INFO.item_seq, ITEM_INFO.item_name, 
                    ITEM_CLASS.class_name, ENTP_INFO.entp_name
                FROM 
                    ITEM_INFO
                LEFT OUTER JOIN 
                    ITEM_CLASS
                ON
                    ITEM_CLASS.class_no = ITEM_INFO.class_no
                LEFT OUTER JOIN
                    ENTP_INFO
                ON
                    ENTP_INFO.entp_seq = ITEM_INFO.entp_seq
                WHERE 
                    ITEM_INFO.item_seq = {itemSeq}
                '''
    basic = db_class.executeOne(basic_sql)
    shape_sql = f'''
                    SELECT 
                        chart, form_code_name, drug_shape, color_class1
                    FROM 
                        ITEM_SHAPE
                    LEFT OUTER JOIN
                        ITEM_INFO    
                    ON
                        ITEM_INFO.item_seq = ITEM_SHAPE.item_seq
                    WHERE 
                        ITEM_INFO.item_seq = {itemSeq}
                '''
    shape       = db_class.executeOne(shape_sql)
    take_sql = f'''
                SELECT 
                    item_stor, item_ingr
                FROM 
                    ITEM_TAKE
                LEFT OUTER JOIN 
                    ITEM_INFO 
                ON 
                    ITEM_INFO.item_seq = ITEM_TAKE.item_seq 
                WHERE 
                    ITEM_INFO.item_seq = {itemSeq}
                '''
    take       = db_class.executeOne(take_sql)

    return render_template('/main/result.html',
                            image=image,
                            name=name,
                            percentage=percentage,
                            basic=basic,
                            shape=shape,
                            take=take)