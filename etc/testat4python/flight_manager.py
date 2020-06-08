# flight_manager.py

'''
This console application manages the information used by an air travel catering company.
'''

import pandas as pd
from numpy import nan
import urllib.request
from datetime import datetime, timedelta
from hashids import Hashids
import random

import data.examples


# airports_url = 'https://raw.githubusercontent.com/datasets/airport-codes/master/data/airport-codes.csv'  # Only using local copy while testing
airports_fallback = 'data\\airport-codes.csv'

# countries_url = 'https://raw.githubusercontent.com/datasets/country-codes/master/data/country-codes.csv'  # Only using local copy while testing
countries_fallback = 'data\\country-codes.csv'

flights = []
dishes = []

def load_airports():
    try:
        filename = airports_url  # Try to get current list from dataset mirror on github
        urllib.request.urlopen(airports_url).getcode()
    except:
        print('Cannot reach airport dataset. Using local copy...')
        filename = airports_fallback  # Local copy as fallback if that fails for any reason

    # Read csv source file into a pandas dataframe for faster lookup
    airport_reader = pd.read_csv(filename, usecols=lambda column: column not in [
                                 'elevation_ft', 'gps_code', 'local_code', 'coordinates', 'ident'], engine='c', keep_default_na=False)
    # Drop airports without IATA code (small airports/helipads etc)
    airport_reader['iata_code'].replace('', nan, inplace=True)
    airports_iata = airport_reader.dropna(subset=['iata_code'])
    airports_indexed = airports_iata.set_index('iata_code')
    # Add suffixes to duplicate iata values to avoid faulty output
    airports_indexed.index = airports_indexed.index + airports_indexed.groupby(level=0).cumcount().astype(str).replace('0','')
    return airports_indexed
airports = load_airports()


def load_countries():
    try:
        filename = countries_url  # Try to get current list from dataset mirror on github
        urllib.request.urlopen(countries_url).getcode()
    except:
        print('Cannot reach country dataset. Using local copy...')
        filename = countries_fallback  # Local copy as fallback if that fails for any reason

    # Read csv source file into a pandas dataframe for faster lookup
    country_reader = pd.read_csv(filename, usecols=[
                                 'ISO3166-1-Alpha-2', 'CLDR display name'], engine='c', keep_default_na=False)
    countries_indexed = country_reader.set_index('ISO3166-1-Alpha-2')
    return countries_indexed
countries = load_countries()

def get_airport_data(origin_airport_iata, destination_airport_iata):
    origin_data = dict(airports.loc[origin_airport_iata])
    destination_data = dict(airports.loc[destination_airport_iata])
    return origin_data, destination_data

def get_country_data(origin_iso_country, destination_iso_country):
    try:
        origin_country = dict(countries.loc[origin_iso_country])[
            'CLDR display name']
    except KeyError:
        origin_country = 'NaN'
    try:
        destination_country = dict(countries.loc[destination_iso_country])[
            'CLDR display name']
    except KeyError:
        destination_country = 'NaN'
    return origin_country, destination_country


class Dish():

    dish_hashid = Hashids()
    dish_data_fields = [
        'dish_data',
        'dish_id',
        'dish_shortname',
        'dish_name',
        'is_vegetarian',
        'is_vegan',
        'is_alcohol',
        'allergens',
        'dish_type',
        'dish_type_short',
        'price',
        'weight',
        'calories',
        'flights'
    ]

    dish_types = {'dessert': 'DST', 'warm-lunch': 'WLU', 'cold-lunch': 'CLU', 'warm-dinner': 'WDI', 'cold-dinner': 'CDI',
                  'warm-breakfast': 'WBR', 'cold-breakfast': 'CBR', 'sidedish': 'SDE', 'warm-beverage': 'WBV', 'cold-beverage': 'CBV', 'snack': 'SNA'}

    __slots__ = dish_data_fields

    def __init__(self, dish_data: dict):
        self.dish_data = dish_data

        self.dish_name = dish_data['dish_name']
        self.is_vegetarian = dish_data['is_vegetarian']
        self.is_vegan = False if not self.is_vegetarian else dish_data['is_vegan']
        self.is_alcohol = dish_data['is_alcohol']
        self.allergens = dish_data['allergens'] if 'allergens' in dish_data else []
        self.dish_type = dish_data['dish_type']
        self.dish_type_short = self.dish_types[self.dish_type]
        self.price = dish_data['price']
        self.weight = dish_data['weight']
        self.calories = dish_data['calories']

        dish_to_ints = ''
        for x in [ord(char) + 2 for char in (self.dish_name.replace(" ", "").lower() + self.dish_type_short).lower()]:
            dish_to_ints += (str(x))

        self.dish_id = self.dish_hashid.encode(int(self.price * 100), self.weight, self.calories, int(
            self.is_vegetarian), int(self.is_vegan), int(self.is_alcohol), int(dish_to_ints))
        self.dish_shortname = f'{self.dish_id[:3].upper()}_{self.dish_name.replace(" " , "").replace("-", "").replace("_", "").upper()[:6]}-{self.dish_type_short}_{int(self.price * 100)}_{int(self.is_vegetarian)}{int(self.is_vegan)}{int(self.is_alcohol)}'

        self.flights = []

    def add_flight(self, flight):
        if not isinstance(flight, Flight): 
            raise TypeError
        
        self.flights.append(flight)

    def remove_flight(self, flight):
        self.flights.remove(flight)
        print(f'Removed {flight} from {self} in dish record.')

    def __str__(self):
        return self.dish_shortname

    def __repr__(self):
        return f'Dish({self.dish_data})'

    def delete(self):
        while self.flights:
            for flight in self.flights:
                flight.remove_dish(self)
        dishes.remove(self)
        print(f'Deleting dish {self}...')
        del self


class Flight():

    flight_hashid = Hashids()
    flight_data_fields = [
        'flight_data',
        'flight_id',
        'flight_shortname',
        'flight_description',
        'origin_airport_iata',
        'destination_airport_iata',
        'origin_data',
        'destination_data',
        'origin_airport_name',
        'destination_airport_name',
        'origin_iso_country',
        'destination_iso_country',
        'origin_country',
        'destination_country',
        'origin_municipality',
        'destination_municipality',
        'origin_iso_region',
        'destination_iso_region',
        'departure_time',
        'arrival_time',
        'flight_duration',
        'is_international',
        'is_intercontinental',
        'flight_type',
        'passengers',
        'passenger_count',
        'dishes'
    ]

    __slots__ = flight_data_fields

    def __init__(self, flight_data: dict):
        self.flight_data = flight_data

        self.origin_airport_iata = flight_data['origin_airport_iata']
        self.destination_airport_iata = flight_data['destination_airport_iata']
        self.origin_data, self.destination_data = get_airport_data(
            self.origin_airport_iata, self.destination_airport_iata)

        self.origin_airport_name = self.origin_data['name']
        self.destination_airport_name = self.destination_data['name']

        self.origin_iso_country = self.origin_data['iso_country']
        self.destination_iso_country = self.destination_data['iso_country']
        self.origin_country, self.destination_country = get_country_data(
            self.origin_iso_country, self.destination_iso_country)

        self.origin_iso_region = self.origin_data['iso_region']
        self.destination_iso_region = self.destination_data['iso_region']
        self.origin_municipality = self.origin_data['municipality']
        self.destination_municipality = self.destination_data['municipality']

        self.passengers = flight_data['passengers']
        self.passenger_count = sum(self.passengers.values())

        self.departure_time = flight_data['departure_time']
        self.arrival_time = flight_data['arrival_time']
        self.flight_duration = self.arrival_time - self.departure_time

        self.is_intercontinental = True if self.origin_data[
            'continent'] is not self.destination_data['continent'] else False
        self.is_international = True if self.origin_data[
            'iso_country'] is not self.destination_data['iso_country'] else False
        self.flight_type = 'Intercontinental' if self.is_intercontinental else 'International' if self.is_international else 'Domestic'

        iata_to_ints = ''
        for x in [ord(char) + 2 for char in (self.origin_airport_iata + self.destination_airport_iata).lower()]:
            iata_to_ints += (str(x))

        self.flight_id = self.flight_hashid.encode(self.passenger_count, int(self.departure_time.strftime(
            "%y%m%d%H%M")), int(self.flight_duration.total_seconds()), int(iata_to_ints))
        self.flight_shortname = f'{self.flight_id[:3].upper()}_{self.origin_airport_iata}-{self.destination_airport_iata}_{self.departure_time.strftime("%y%m%d%H%M")}'
        self.flight_description = f'{self.flight_type} flight from {self.origin_airport_name} in {self.origin_municipality}{", " + self.origin_country if self.is_international else ""} to {self.destination_airport_name} in {self.destination_municipality}{", " + self.destination_country if self.is_international else ""}, departing on {self.departure_time.strftime("%d.%m.%y")} at {self.departure_time.strftime("%H:%M")} with {self.passenger_count} passengers.'


        self.dishes = []

    def add_dish(self, dish):
        if not isinstance(dish, Dish):
            raise TypeError

        dish.add_flight(self)
        self.dishes.append(dish)

    def remove_dish(self, dish):
        dish.remove_flight(self)
        self.dishes.remove(dish)
        print(f'Removed {dish} from {self} in flight record.')

    def __str__(self):
        return self.flight_shortname

    def __repr__(self):
        return f'Flight({self.flight_data})'

    def delete(self):
        while self.dishes:
            for dish in self.dishes:
                self.remove_dish(dish)
        flights.remove(self)
        print(f'Deleting flight {self}...')
        del self


def new_flight(flight_data: dict) -> Flight:
    flight = Flight(flight_data)
    flights.append(flight)
    print(f'Flight {flight.flight_shortname} added.')
    return flight.flight_id

def new_dish(dish_data: dict) -> Dish:
    dish = Dish(dish_data)
    dishes.append(dish)
    print(f'Dish {dish.dish_shortname} added.')
    return dish.dish_id


class Flight_Generator():

    def __init__(self):
        # Could put parameters to customize generated flights here
        return

    def __call__(self, x=1):
        response = []
        for i in range(x):
            try:
                flight_data = {key:0 for key in Flight.flight_data_fields}
                
                flight_data['origin_airport_iata'] = airports.sample().index[0]
                flight_data['destination_airport_iata'] = airports.sample().index[0]
                if flight_data['origin_airport_iata'] == flight_data['destination_airport_iata']:
                    self.__call__()
                    return

                Y = random.randint(2020, 2100)
                M = random.randint(1, 12)
                if M in (1, 3, 5, 7, 8, 10, 12):
                    D = random.randint(1, 31)
                elif M in (4, 6, 9, 11):
                    D = random.randint(1, 28)
                elif M == 2:
                    D = random.randint(1, 30)
                h = random.randint(0, 23)
                m = random.randint(0, 59)
                hd = random.randint(2, 18)
                ma = random.randint(0, 59)
                try:
                    dt = datetime(Y, M, D, h, m, 00, 000)
                    at = dt + timedelta(hours=hd, minutes=ma)
                except:
                    self.__call__()
                    return
                flight_data['departure_time'] = dt
                flight_data['arrival_time'] = at

                fc = random.randint(0, 16)
                bc = random.randint(0, 80)
                ec = random.randint(0, 450)
                flight_data['passengers'] = {'first_class': fc, 'business_class:': bc, 'economy_class': ec}

                flight = new_flight(flight_data)
                print('Random flight generated.')
                response.append(flight)
            except:
                continue
        return response


class Dish_Generator():
    allergens = ['peanuts', 'tree nuts', 'milk', 'eggs', 'fish', 'shellfish', 'soy', 'wheat', 'sesame seeds', 'mustard', 'sulphites', 'gluten', 'celery', 'lupin']

    def __init__(self):
        # Could put parameters to customize generated dishes here
        return

    def __call__(self, x=1):
        response = []
        for i in range(x):
            try:
                dish_data = {key:0 for key in Dish.dish_data_fields}

                dish_data['dish_type'] = random.choice(Dish.dish_types.keys)
                dish_data['is_vegetarian'] = random.choice([False, True])
                dish_data['is_vegan'] = random.choice([False, True]) if dish_data['is_vegetarian'] == True else False
                dish_data['is_alcohol'] = random.choice([False, True]) if dish_data['is_vegetarian'] == 'cold-beverage' else False
                dish_data['allergens'] = random.choices(population=allergens, k=random.randint(0, 5))
                dish_data['price'] = round(random.random(0.5, 25), 2)
                dish_data['weight'] = random.randint(25, 500)
                dish_data['calories'] = random.randint(50, 1000)
                dish_data['dish_name'] = 


# Demo Code
for dish in data.examples.example_dishes:
    new_dish(dish)

for flight in data.examples.example_flights:
    new_flight(flight)

fgen = Flight_Generator()
fgen(5)

for flight in flights:
    print(flight.flight_description)
# ---
