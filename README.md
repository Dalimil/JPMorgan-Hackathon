# Urban Roots

## TODO

api.py -> /remove_issue implement functionality (remove an issue with a give issue_id)

main.py -> report receives a file ...where to save it


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
https://4019e606.ngrok.com
```