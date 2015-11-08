# Urban Roots

## Screenshots

![01](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_10-58-53.png)
![02](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_11-09-04.png)
![03](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_11-13-23.png)
![04](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_11-15-07.png)
![05](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_10-59-56.png)


## Installation

#### Install required packages
```sh
sudo apt-get install libpq-dev python-dev
pip install -r requirements.txt
```

#### Start the server (localhost:8080):
```sh
python main.py
```

### Update database:
```sh
python manage.py db init
python manage.py db migrate
python manage.py db upgrade
```

### Domain:
```sh
https://275ced70.ngrok.com
```