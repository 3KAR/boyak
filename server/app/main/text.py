from flask import Blueprint, request, render_template, flash, redirect, url_for, jsonify
from flask import current_app as app
from app.module import dbModule
 
text= Blueprint('text', __name__, url_prefix='/text')
db_class= dbModule.Database()

@text.route('/', methods=['GET'])
def index():
      item_name = request.args.get('item_name')
      if item_name is None:
            item_name = '우루사'
      sql = f'''
            SELECT 
                  ITEM_INFO.item_name, ITEM_CLASS.class_name
            FROM 
                  ITEM_CLASS
            LEFT JOIN
                  ITEM_INFO
            ON
                  ITEM_INFO.class_no = ITEM_CLASS.class_no
            WHERE
                  ITEM_INFO.item_name
            LIKE '%{item_name}%'
            LIMIT 
                  5
            '''  
      res = db_class.executeAll(sql)
      print(res)
      return jsonify({'res':res})