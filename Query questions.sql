--Find car models that have not been parked at all:
SELECT cm.model_name
FROM car_models cm
LEFT JOIN parking p ON cm.model_name = p.carName
WHERE p.id IS NULL;
--Find available parking places for a given date range:
SELECT parkingplace FROM parking WHERE dateTo < '2024-01-01' OR dateFrom > '2024-01-05';
--Find the parking entries for a specific user:
SELECT * FROM parking WHERE name = 'Leo Thomas';
--Retrieve the total number of parkings for each car model per month:
SELECT carName, EXTRACT(MONTH FROM dateFrom) AS month, COUNT(*) AS parkings
FROM parking
GROUP BY carName, month;
--List all cars parked on a specific date:
SELECT *
FROM parking
WHERE '2026-05-15' BETWEEN datefrom AND dateto;
--Find the most popular parking place based on the number of times it has been used:
SELECT parkingPlace, COUNT(*) AS usage_count
FROM parking
GROUP BY parkingPlace
ORDER BY usage_count DESC
LIMIT 1;
--Find all clients names that start with the letter 'L':
SELECT DISTINCT name, carname, carnumber
FROM parking
WHERE name LIKE 'L%';
--Find all cars with a specific model parked on a specific date:
SELECT *
FROM parking
WHERE carName = 'Toyota Camry' AND '2024-06-01' BETWEEN dateFrom AND dateTo;











