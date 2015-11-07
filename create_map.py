def create_map(style, markers, info_boxes):
    mymap = Map(
        identifier="view-side",
        lat=55.855478,
        lng=-4.2560766,
        style=style,
        zoom="16",
        maptype="TERRAIN",
        infobox=info_boxes,
        markers=markers)
    return mymap