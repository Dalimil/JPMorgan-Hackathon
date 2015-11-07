from flask import Blueprint, request
from models import User
import json

api = Blueprint('api', __name__, template_folder='templates')

@api.route("/authenticate", methods=['POST'])
def authenticate():
	print request
	user = json.loads(request.data)
	print user
	q = User.query.filter_by(email=user["email"], password=user["password"]).first()
	print q
	if q:
		return json.dumps({"result":True})
	else:
		return json.dumps({"result":False})

@api.route("/create_user", methods=['POST'])
def create_user():
	user_json = json.loads(request.data)
	user = User(user_json.first_name, user_json.last_name, user_json.email, user_json.password)
	db.session.add(user)
	q = db.session.commit()
	if q:
		return json.dumps({"result":True})
	else:
		return json.dumps({"result":False})