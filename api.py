from flask import Blueprint, request
from models import User
import json

api = Blueprint('api', __name__, template_folder='templates')

@api.route("/authenticate", methods=['POST'])
def authenticate():
	user = json.loads(request.data)
	q = User.query.filter_by(email=user["email"], password=user["password"]).first()
	if q:
		return json.dumps({"result":True})
	else:
		return json.dumps({"result":False})