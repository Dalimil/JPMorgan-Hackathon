# JPMorgan Chase - "Code for Good" Hackathon (Glasgow, 7-8th Nov 2015)

**Target non-profit organization:** Urban Roots 

**Team:** [Dalimil Hajek](https://github.com/dalimil), [Miguel Jaques](https://github.com/seuqaj114), [Willy Dinata](https://github.com/whdinata), [Michael Ahari](https://github.com/MichaelAhari), [KamenB](https://github.com/KamenB)

## Screenshots

![01](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_10-58-53.png)
![02](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_11-09-04.png)
![03](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_11-13-23.png)
![04](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_11-15-07.png)
![05](https://github.com/Glasgow2015/team-5/blob/master/screenshots/Screenshot_2015-11-08_10-59-56.png)


## Installation

*Please note that all the code was written as quickly as possible (in just 24 hours) and is therefore of poor quality and lacks any documentation.*

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
