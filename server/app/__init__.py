from flask import Flask
 
app= Flask(__name__)
 
from app.main.index import main as main
from app.main.result import result as result
from app.main.detail import detail as detail
from app.main.text import text as text
from app.test.test import test as test

app.register_blueprint(main)
app.register_blueprint(result)
app.register_blueprint(detail)
app.register_blueprint(text)
app.register_blueprint(test)