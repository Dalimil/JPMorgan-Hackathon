from flask import Flask
from flask import render_template, request, redirect, session, url_for, escape, make_response, flash, abort
from flask.ext.sqlalchemy import SQLAlchemy
import os

app = Flask(__name__)
app.secret_key = "bnNoqxXSgzoXSOezxpfdvadrMp5L0L4mJ4o8nRzn"

app.config['SQLALCHEMY_DATABASE_URI'] = "postgres://wigdnybtvbxjyl:VI5y4w1SgdVdoEDUyCFBmKyqVH@ec2-46-137-72-123.eu-west-1.compute.amazonaws.com:5432/dd5fh71aujkvoq"
db = SQLAlchemy(app)

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(80))
    last_name = db.Column(db.String(80))
    email = db.Column(db.String(120), unique=True)
    password = db.Column(db.String(40))

    def __init__(self, first_name, last_name, email, password):
        self.first_name = first_name
        self.last_name = last_name
        self.email = email
        self.password = password

    def __str__(self):
        return "{0} {1} {2} {3}".format(self.first_name, self.last_name, self.email, self.password)

@app.route('/')
def index():
    email = None
    if 'email' in session:
        #loggedIn
        email = session['email']
        print('Logged in as {}'.format(email))

    return render_template('index.html', email=email)

@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        email = request.form['email']
        password = request.form['password']
        # check credentials against database here
        user = User.query.filter_by(email=email, password=password).first()
        if(user):
            session['email'] = email
            return redirect(url_for('index'))
        else:
            print "Bad login"
    
    return render_template('login.html')

@app.route('/register', methods=['POST'])
def register():
    #handle new user registration here

    # get form data
    # name = request.form['name']
    # ...

    #save database

    # log them in
    #session['email'] = email

    # redirect
    return redirect(url_for('index'))

@app.route('/logout')
def logout():
    # close session and redirect to index
    session.pop('username', None)
    session.pop('admin', None)
    return redirect(url_for('index'))

@app.route('/admin', methods=['GET', 'POST'])
def admin():
    loggedIn = False
    if request.method == 'POST':
        email = request.form['email']
        password = request.form['password']
        if(email=='admin' and password == 'admin'):
            session['admin'] = True
        return redirect(url_for('admin'))
    else:
        if 'admin' in session:
            #logged in as admin
            loggedIn = True


    return render_template('admin.html', loggedIn=loggedIn)

@app.route('/report')
def report():
    return render_template('report.html')

if __name__ == '__main__':
    app.run(port=8080, debug=True) 
