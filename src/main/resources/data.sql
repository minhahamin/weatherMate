-- Recommendation data initialization

-- Clear & Hot weather (25°C and above)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Clear', 25.0, 100.0, 'Tank top, Shorts, Sunglasses, Sunscreen', 'Cold noodles, Shaved ice, Ice cream, Cold drinks', 'Swimming, Water activities, Outdoor walking', '☀️🌞');

-- Clear & Moderate weather (15-25°C)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Clear', 15.0, 25.0, 'Long sleeve t-shirt, Light jacket', 'Sandwich, Pasta, Coffee', 'Walking, Cycling, Park outing', '🌤️☀️');

-- Clear & Cold weather (below 15°C)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Clear', -50.0, 15.0, 'Coat, Knit, Scarf', 'Hot rice soup, Stew, Tea', 'Cafe, Indoor activities', '🧥☀️');

-- Rain & Warm weather (15°C and above)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Rain', 15.0, 100.0, 'Umbrella, Long sleeves, Waterproof jacket', 'Hot soup, Pancake, Makgeolli', 'Indoor cafe, Reading, Movie', '☔🌧️');

-- Rain & Cold weather (below 15°C)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Rain', -50.0, 15.0, 'Umbrella, Coat, Waterproof jacket, Warm clothes', 'Ramen, Kimchi stew, Hot soup, Hot bun', 'Indoor activities, Movie, Sauna', '☔🥶');

-- Cloudy & Warm weather (18°C and above)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Clouds', 18.0, 100.0, 'Cardigan, Long sleeve shirt', 'Coffee, Brunch, Pasta', 'Shopping, Cafe tour, Museum', '☁️');

-- Cloudy & Moderate weather (10-18°C)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Clouds', 10.0, 18.0, 'Knit, Jacket', 'Hot coffee, Bread, Soup', 'Reading, Indoor exercise, Cafe', '☁️🌫️');

-- Cloudy & Cold weather (below 10°C)
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Clouds', -50.0, 10.0, 'Thick coat, Scarf, Gloves', 'Rice soup, Stew, Hot drinks', 'Indoor activities, Movie, Reading', '☁️🧥');

-- Snow
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Snow', -50.0, 100.0, 'Padded jacket, Thick coat, Gloves, Scarf, Hat', 'Fish bread, Hot bun, Roasted sweet potato, Hot chocolate', 'Building snowman, Indoor activities', '❄️⛄');

-- Mist
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Mist', -50.0, 100.0, 'Moderate jacket, Light scarf', 'Hot tea, Coffee, Soup', 'Quiet cafe, Reading, Meditation', '🌫️');

-- Thunderstorm
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Thunderstorm', -50.0, 100.0, 'Umbrella, Waterproof jacket, Safe clothing', 'Ramen, Warm food indoors', 'Stay indoors, Movie, Games', '⚡🌩️');

-- Drizzle
INSERT INTO recommendations (weather_condition, min_temp, max_temp, clothing, food, activity, emoji)
VALUES ('Drizzle', -50.0, 100.0, 'Light umbrella, Waterproof jacket', 'Hot coffee, Bread, Soup', 'Indoor cafe, Shopping', '🌦️');