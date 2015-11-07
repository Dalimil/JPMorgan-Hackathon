from flask import Blueprint, request
from models import User, Project, db
import json

api = Blueprint('api', __name__, template_folder='templates')

def row2dict(row):
    d = {}
    for column in row.__table__.columns:
        d[column.name] = str(getattr(row, column.name))

    return d


@api.route("/authenticate", methods=['POST'])
def authenticate():
	user = json.loads(request.data)
	q = User.query.filter_by(email=user["email"], password=user["password"]).first()
	if q:
		return json.dumps({"result":True})
	else:
		return json.dumps({"result":False})

@api.route("/create_user", methods=['POST'])
def create_user():
	user = json.loads(request.data)
	user = User(user["first_name"], user["last_name"], user["email"], user["password"], user["address"],user["phone"])
	db.session.add(user)
	db.session.commit()
	
	return json.dumps({"result":True})

@api.route("/create_project", methods=['POST'])
def create_project():
	user = json.loads(request.data)
	user = Project(user["name"], user["address"], user["description"], user["num_people"], user.get("image",None))
	db.session.add(user)
	db.session.commit()
	
	return json.dumps({"result":True})

@api.route("/add_project", methods=['POST'])
def add_projects():
	data = json.loads(request.data)
	user = User.query.filter_by(email=data["email"]).first()

	p = Project.query.filter_by(id=int(data["project_id"])).first()
	p.users.append(user)

	db.session.commit()

	return json.dumps({"result":True})

@api.route("/projects")
@api.route("/projects/<email>")
def projects(email=None):
	if email:
		proj_list = [row2dict(p) for p in Project.query.filter(Project.users.any(email=email)).all()]
	else:
		proj_list = [row2dict(p) for p in Project.query.all()]
	print proj_list
	return json.dumps({"data":proj_list})
