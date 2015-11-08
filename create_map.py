from flask.ext.googlemaps import Map

def create_map(style, markers, info_boxes, id):
    mymap = Map(
        identifier=id,
        lat=55.855478,
        lng=-4.2560766,
        style=style,
        zoom='10',
        maptype="TERRAIN",
        # Infobox is a list of a sequence of html tags. Example ["<img src='./static/a.jpg' height = 100 width = 100>", "<span><p>Text</p></span>"]
        # Just plain text does not display in infoboxes. It has to be put into a tag.
        infobox=info_boxes,
        # Markers is a list of tuples of coordinates. Example: {image:[(45.223, 92.114)]}
        markers=markers)
    return mymap