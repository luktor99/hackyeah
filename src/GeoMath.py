class GeoMath:
    def __init__(self, lat, lng):
        self.lat = lat
        self.lng = lng

    def get_entry_point(self):
        return {'lat': self.lat,
                'lng': self.lng}
