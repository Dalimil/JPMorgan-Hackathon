from main import app
ctx = app.app_context()
ctx.push()

from main import db
from models import Project

for project in Project.query.all():
	print str(project)

ctx.pop()