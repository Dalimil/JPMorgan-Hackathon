from flask import Flask
from flask import render_template, request, redirect, session, url_for, escape, make_response, flash, abort

app = Flask(__name__)
app.secret_key = "bnNoqxXSgzoXSOezxpfdvadrMp5L0L4mJ4o8nRzn"


@app.route('/')
def index():
	if 'username' in session:
		# loggedIn
		username = session['username']
        print('Logged in as {}'.format(name))

    return render_template('index.html')

@app.route('/login', methods=['GET', 'POST'])
def login():
	if request.method == 'POST':
		username = request.form['username']
		passw = request.form['password']
		# check credentials against database here
		valid = True
		if(valid):
			return redirect(url_for('index'))
	
    return render_template('login.html')

@app.route('/register', methods=['POST'])
	#handle new user registration here

	# get form data
	# name = request.form['name']
	# ...

	#save database

	# log them in
	#session['username'] = request.form['username']

	# redirect
	return redirect(url_for('index'))

@app.route('/logout')
def logout():
	# close session and redirect to index
	session.pop('username', None)
    return redirect(url_for('index'))

@app.route('/admin', methods=['GET', 'POST'])
def admin():
	loggedIn = False
	if request.method == 'POST':
		username = request.form['username']
		password = request.form['password']
		if(username=='admin' && password == 'admin'):
			session['admin'] = True
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