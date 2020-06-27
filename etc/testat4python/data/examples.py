# examples.py

from datetime import datetime, timedelta

example_dishes = [
    {'dish_name': 'Spam with ham', 'is_vegetarian': False, 'dish_type': 'warm-breakfast',
     'price': 2.5, 'weight': 320, 'calories': 410, 'is_alcohol': False},
    {'dish_name': 'Egg salad', 'is_vegetarian': True, 'is_vegan': False, 'allergens': ['eggs'], 'dish_type': 'sidedish',
     'price': 1.3, 'weight': 136, 'calories': 294, 'is_alcohol': False},
    {'dish_name': 'Snickers bar', 'is_vegetarian': True, 'is_vegan': False,  'allergens': ['peanuts'], 'dish_type': 'snack',
     'price': 1.1, 'weight': 90, 'calories': 325, 'is_alcohol': False},
    {'dish_name': 'Coca-Cola 0.25ml', 'is_vegetarian': True, 'is_vegan': True,  'allergens': ['sulfites'], 'dish_type': 'cold-beverage',
     'price': 1.5, 'weight': 300, 'calories': 220, 'is_alcohol': False},
]

example_flights = [
    {'origin_airport_iata': 'LAX', 'destination_airport_iata': 'CDG', 'passengers': {'first_class': 11, 'business_class:': 23, 'economy_class': 109},
     'departure_time': datetime(2020, 4, 26, 21, 53, 00, 000), 'arrival_time': datetime(2020, 4, 27, 4, 53, 00, 000)},
    {'origin_airport_iata': 'BER', 'destination_airport_iata': 'LHR', 'passengers': {'first_class': 4, 'business_class:': 12, 'economy_class': 76},
     'departure_time': datetime(2020, 5, 27, 6, 53, 00, 000), 'arrival_time': datetime(2020, 5, 27, 9, 14, 00, 000)},
]
