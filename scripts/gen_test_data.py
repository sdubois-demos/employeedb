import json
import sys
from faker import Faker

# Initialize Faker
fake = Faker()

# Check if the correct number of arguments is provided
if len(sys.argv) != 3:
    print("Usage: python generate_users.py <number_of_users> <output_file_path>")
    sys.exit(1)

# Get the number of users and output file path from the command-line arguments
try:
    num_users = int(sys.argv[1])
    file_path = sys.argv[2]
except ValueError:
    print("Please provide a valid integer for the number of users.")
    sys.exit(1)

# Generate the specified number of random users
users = [
    { "firstName": fake.first_name(), "lastName": fake.last_name(), "email": fake.email() }
    for _ in range(num_users)
]

# Save to the specified JSON file
with open(file_path, "w") as json_file:
    json.dump(users, json_file, indent=2)

print(f"Generated {num_users} users and saved to {file_path}")

