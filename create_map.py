def create_map(post_code, style):
	r = requests.post(
		url="http://maps.googleapis.com/maps/api/geocode/json?address=" + post_code, 
		data=json.dumps({})
	)
	coordinates_object = json.loads(r.text)["results"][0]["geometry"]["bounds"]["northeast"]

	mymap = Map(
    	identifier="view-side",
    	lat=coordinates[0],
    	lng=coordinates[1],
    	style=style,
    	zoom="15",
    	maptype="TERRAIN",
    	infobox=["<img src='http://www.keenthemes.com/preview/conquer/assets/plugins/jcrop/demos/demo_files/image1.jpg' height=100 width=100>"],
    	markers=[coordinates])
	return mymap