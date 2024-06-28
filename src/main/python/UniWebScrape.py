import requests
from bs4 import BeautifulSoup
import re
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
import time
import json

# URL of the website to scrape
url = 'https://www.torontomu.ca/calendar/2023-2024/courses/'

# Send a GET request to the website
response = requests.get(url)

# Parse the HTML content of the webpage
soup = BeautifulSoup(response.content, 'html.parser')

# Make the course_link to avoid scraping it
def construct_course_link(course_name):
    course_name_cleaned = re.sub(r'\s*\(.*?\)', '', course_name)
    course_slug = course_name_cleaned.lower().replace(' ', '-')
    return f"{url}{course_slug}/"

# Function to scrape course details from a course page using Selenium
def scrape_courses(course_link, course_program):

    # Set up Chrome WebDriver
    chrome_path = "chromedriver.exe"
    chrome_options = Options()
    chrome_options.add_argument("--headless")  # Run Chrome in headless mode
    service = Service(chrome_path)
    driver = webdriver.Chrome(service=service, options=chrome_options)

    driver.get(course_link)

    # Wait for the page to fully load (you may need to adjust the time based on page load speed)
    time.sleep(2)

    # Find all course elements (example)
    course_elements = driver.find_elements(By.CLASS_NAME, 'courseCode')
    course_description_elements = driver.find_elements(By.CLASS_NAME, 'courseDescription')

    # Extract data from course elements
    courses = []
    for course_element, course_description_element in zip(course_elements, course_description_elements):
        course_name = course_element.text.strip()
        course_description = course_description_element.text.strip()

        # Remove leading "Course Description" if present
        if course_description.startswith("Course Description"):
            course_description = course_description[len("Course Description"):].strip()

        course_code = course_name[:7]
        course_title = course_name[10:]

        course_data = {
            "course_program": course_program,
            "course_code": course_code,
            "course_name": course_title,
            "course_description": course_description
        }
        courses.append(course_data)

    # Close the browser
    driver.quit()

    return courses

# Check if table exists
all_courses = []
table = soup.find('table')
if table:
    tbody = table.find('tbody')
    if tbody: 
        for index, row in enumerate(tbody.find_all('tr')):
            if index == 0:
                continue

            course = row.find('a')
            if course:
                course_name = course.text.strip()
                course_link = construct_course_link(course_name)
                
                print(f'Course Category: {course_name}')

                # Scrape additional details for course page and collect the data
                course_data = scrape_courses(course_link, course_name)
                all_courses.extend(course_data)

else:
    print('No table found on the webpage')

# Save the data to a JSON file
output_file = 'courses.json'
with open(output_file, 'w', encoding='utf-8') as file:
    json.dump(all_courses, file, indent=4)

print("Data has been scraped and saved to courses.json")
