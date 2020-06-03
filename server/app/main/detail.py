 
from flask import Blueprint, request, render_template, flash, redirect, url_for
from flask import current_app as current_app
 
from app.module import dbModule
import base64

detail = Blueprint('detail', __name__, url_prefix='/detail')
db_class= dbModule.Database()

@detail.route('/', methods=['GET'])
def index():
    itemSeq = request.args.get('item_seq')
    if itemSeq is None:
        itemSeq = 197000040
    img_sql = f'SELECT item_img FROM ITEM_IMG WHERE item_seq = {itemSeq}'
    tmp = db_class.executeOne(img_sql)
    image = base64.b64encode(tmp['item_img']).decode("utf-8")

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
    return render_template('/main/detail.html',
                            image=image,
                            basic=basic,
                            shape=shape,
                            take=take)