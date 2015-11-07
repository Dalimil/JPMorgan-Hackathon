r = requests.post(
	url="https://maps.googleapis.com/maps/api/geocode/json?address=" + post_code, 
	data=json.dumps({})
)
coordinates_object = json.loads(r.text)["results"][0]["geometry"]["bounds"]["northeast"]