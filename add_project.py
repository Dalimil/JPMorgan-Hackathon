from main import app
ctx = app.app_context()
ctx.push()

from main import db
from models import Project

project1 = Project("Gardening 101", "This project will involve a lot of the usual gardening stuff.", "1054 Aikenhead Rd Glasgow G44 4TJ, UK", "13")

db.session.add(project1)
db.session.commit()

ctx.pop()