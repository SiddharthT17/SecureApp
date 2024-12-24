**Coding Assignment: Account Creation and ShareCode Generation**
**Objective**
The goal of this assignment is to develop a functional program that implements account creation (both personal and business) with ID verification and generates a unique ShareCode. The ShareCode should be securely linked to a bank account. Follow the requirements below to simulate a real-world business scenario.

**Business Requirements**
You are tasked with developing a system for creating user accounts. The application should support the following functionalities:

**1. Account Creation Flow**
**Personal Account:**
	•	Display the following options on the first screen:
	•	Sign In
	•	Create Account
	•	If the user selects Create Account, collect the following details:
	•	First Name
	•	Last Name
	•	Email ID
	•	Password
	•	Confirm Password
	•	After the user clicks "Sign Up," proceed to ID Verification.

**Business Account: (Nice-to-Have)**
For an optional stretch goal, implement account creation for business users. Create a business account for a small moving company with the following details:
	•	Fleet details: 4 trucks (2 ten-footers, 1 fifteen-footer, and 1 twenty-six-footer).

**2. ID Verification**
	•	On the ID Verification screen:
	•	Prompt the user to upload a Driver's License (DL) or Passport.
	•	Allow file uploads in .jpg or .pdf format only.
	•	Include a Submit button to proceed.

**3. Display Information with ShareCode**
	•	After submission, display a confirmation screen that includes the following:
	•	First Name and Last Name (Display Name)
	•	Email Address
	•	Automatically generated ShareCode
	•	The ShareCode should follow this format:
	•	First 2 letters: Initials of the user's first and last name
	•	Next 2 letters: Abbreviated state name (e.g., NY, NC)
	•	Next 4 digits: Randomly generated 4-digit number
Example: If the user's name is John Doe from New York, the ShareCode could be: JDNY-4567
	•	Include a Copy button next to the ShareCode for easy copying.

**4. Bank Account Integration **
	•	Optionally, allow the user to link their ShareCode to their bank account:
	•	Collect bank account and routing numbers.
	•	Encrypt and securely store this data.
	•	Confirm the linking on the final screen.

**Technical Requirements**
	•	Develop a fully functional program using a language of your choice (e.g., Python, JavaScript, Java, etc.).
	•	Include comments in your code to explain your logic.
	•	Ensure the program adheres to basic coding standards, such as validation for:
	•	Mandatory fields
	•	Password matching
	•	File type restrictions for ID uploads
	•	ShareCode format validation
	•	Use encryption for sensitive data (e.g., passwords, bank account information).

**Submission Instructions**
	•	Provide the source code in a GitHub repository or as a compressed file.
	•	Include a README.md file with:
	•	Instructions to set up and run the program.
	•	A brief explanation of how you implemented each feature.
	•	If you completed the "Nice-to-Have" feature (bank account integration), explain how you handled encryption.

**Evaluation Criteria**
	•	Correctness: Does the application meet all the requirements?
	•	Code Quality: Is the code readable, maintainable, and well-documented?
	•	Security: Is sensitive information handled securely?
	•	Completeness: Are the main functionalities implemented? Are the nice-to-have features attempted?
	•	Creativity: Is the user interface intuitive and user-friendly?

