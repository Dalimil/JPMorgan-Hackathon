from flask import Flask
from flask import render_template, request, redirect, session, url_for, escape, make_response, flash, abort
from flask.ext.sqlalchemy import SQLAlchemy
from flask.ext.sqlalchemy import Model,BaseQuery
import os
from api import api
from models import db
from models import User, Project
import requests
import json
from create_map import create_map
from flask.ext.googlemaps import Map
from flask.ext.googlemaps import GoogleMaps

app = Flask(__name__)
GoogleMaps(app)

app.secret_key = "bnNoqxXSgzoXSOezxpfdvadrMp5L0L4mJ4o8nRzn"

app.config['SQLALCHEMY_DATABASE_URI'] = "postgres://wigdnybtvbxjyl:VI5y4w1SgdVdoEDUyCFBmKyqVH@ec2-46-137-72-123.eu-west-1.compute.amazonaws.com:5432/dd5fh71aujkvoq"
db.init_app(app)
app.register_blueprint(api)

DOMAIN = "https://558e229b.ngrok.com"

@app.route('/')
def index():
    email = None
    all_projects = None
    all_projects_map = None
    my_projects = None
    if 'email' in session:
        #loggedIn
        email = session['email']
        my_projects = json.loads(requests.get(url=DOMAIN+"/projects/"+email).text)["data"]
        all_projects = json.loads(requests.get(url=DOMAIN+"/projects").text)["data"]
        names_my_p = [i["name"] for i in my_projects]
        greens = [(i["lat"], i["lng"]) for i in all_projects if i["name"] in names_my_p]
        reds = [(i["lat"], i["lng"]) for i in all_projects if i["name"] not in names_my_p]
        infoboxReds = ["<p><strong>"+i["name"][:1].upper()+i["name"][1:]+"</strong></p><p><strong>Availability: </strong>"+str(i["count"])+"/"+str(i["num_people"])+"</p>" for i in all_projects if i["name"] not in names_my_p]
        infoboxGreens = ["<p><strong>"+i["name"][:1].upper()+i["name"][1:]+"</strong></p><p><strong>Availability: </strong>"+str(i["count"])+"/"+str(i["num_people"])+"</p>" for i in all_projects if i["name"] in names_my_p]
        
        all_projects_map = create_map("width:100%;height:400px;border: 1px solid black; border-radius: 15px;", {"http://maps.google.com/mapfiles/ms/icons/green-dot.png":greens, "http://maps.google.com/mapfiles/ms/icons/red-dot.png":reds}, infoboxGreens+infoboxReds)
        all_projects = [i for i in all_projects if i["name"] not in names_my_p]
        print(my_projects)
        print(all_projects)
        print('Logged in as {}'.format(email))

    return render_template('index.html', email=email, all_projects=all_projects, all_projects_map=all_projects_map, my_projects=my_projects)

@app.route('/login', methods=['POST'])
def login():
    email = request.form['email']
    password = request.form['password']
    # check credentials against database here
    r = requests.post(url=DOMAIN+"/authenticate",data=json.dumps({"email":email, "password":password}))
    
    auth = json.loads(r.text)["result"]

    if(auth):
        session['email'] = email
    else:
        print "Bad login"
    
    return redirect(url_for("index"))

@app.route('/register', methods=['POST'])
def register():
    #handle new user registration here
    first_name = request.form['first_name']
    last_name = request.form['last_name']
    email = request.form['email']
    password = request.form['password']
    address = request.form['address']
    phone = request.form['phone']
    r = requests.post(url=DOMAIN+"/create_user",
        data=json.dumps({
            "first_name":first_name,
            "last_name":last_name,
            "email":email, 
            "password":password,
            "address":address,
            "phone":phone}))
    auth = json.loads(r.text)["result"]
    if(auth):
        session['email'] = email
    else:
        print "Error during registration"
    return redirect(url_for("index"))

@app.route('/logout')
def logout():
    # close session and redirect to index
    session.pop('email', None)
    session.pop('admin', None)
    return redirect(url_for('index'))

@app.route('/project_sign_up', methods=['POST'])
def project_sign_up():
    r = requests.post(url=DOMAIN+"/add_project", 
        data=json.dumps({"email":request.form["email"], "project_id":request.form["project_id"]}))
    res = json.loads(r.text)["result"]
    if not res:
        print "Error during project sign up"

    return redirect(url_for('index'))

@app.route('/admin', methods=['GET', 'POST'])
def admin():
    loggedIn = False
    all_projects = None
    all_users = None
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        if(username=='admin' and password == 'admin'):
            session['admin'] = True
        return redirect(url_for('admin'))
    else:
        if 'admin' in session:
            #logged in as admin
            loggedIn = True
            all_projects = json.loads(requests.get(url=DOMAIN+"/projects").text)["data"]
            all_users = json.loads(requests.get(url=DOMAIN+"/users").text)["data"]
            all_projects_map = create_map("width:100%;height:400px;border: 1px solid black; border-radius: 15px;", [(i["lat"], i["lng"]) for i in all_projects], ["<p><strong>"+i["name"][:1].upper()+i["name"][1:]+"</strong></p><p><strong>Availability: </strong>"+str(i["count"])+"/"+str(i["num_people"])+"</p>" for i in all_projects])
            
    return render_template('admin.html', loggedIn=loggedIn, all_projects=all_projects)


@app.route('/admin/volunteer', methods=['GET', 'POST'])
def volunteer():
    if request.method == 'POST':
        firstname = request.form['firstname']
        lastname = request.form['lastname']
        email = request.form['email']
        password = request.form['password']
        address = request.form['address']
        phone = request.form['phone']
    
        newVolunteer = User(firstname,lastname,email,password,address, phone)
        db.session.add(newVolunteer)
        db.session.commit()
         
    users = User.query.all()
    #query = db.session.execute("SELECT * FROM User") 
    return render_template('volunteer.html',users=users);
 

@app.route('/admin/projects',methods=['GET','POST'])
def projects():
    if 'admin' in session:
        if request.method == 'POST':
            name = request.form['name']
            description = request.form['description']
            address = request.form['address']
            num_people = request.form['num_people']
            image = request.form.get("image",None)
            if (name == '' or description == '' or address == '' or num_people == ''):
                print "Could not submit: empty field"
            else:
            	p = Project(name, description, address, int(num_people), image)
            	db.session.add(p)
            	db.session.commit()

    projects = json.loads(requests.get(url=DOMAIN+"/projects").text)["data"]
    return render_template('projects.html',projects=projects);
        
@app.route('/admin/email/<project_id>',methods=['GET','POST'])
def email(project_id):
    if 'admin' in session:
        if request.method == 'POST':
            url = "https://api.mailgun.net/v3/sandboxff7ed2cffc264af087e9442d1e5b02e8.mailgun.org/messages"

            text = request.form["text"]
            subject = request.form["subject"]

            for user in Project.query.filter_by(id=project_id).first().users:
                email = user.email

                data = {"from":"urbanroots@hackathon.com","to":email,"subject":subject,"text":text}

                r = requests.post(url,auth=("api","key-e8921e9f18f175219e090d218a3c6db5"),data=data)

            return redirect(url_for('admin'))
            
        if request.method == 'GET':
            return render_template('email.html',project_id=project_id);


@app.route('/report')
def report():
    return render_template('report.html')

if __name__ == '__main__':
    app.run(port=8080, debug=False, threaded=True) 
