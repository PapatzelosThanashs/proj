<template>
    <div class="registerForm">
    
    <h2>Register New User</h2>
    <form @submit.prevent="createUser">
      <!-- Name (Mandatory) -->
      <div>
        <label for="name">
          <span class="required-asterisk">*</span> Name:
        </label>
        <input type="text" id="name" v-model="newUser.name" />
        <span v-if="errors.name">{{ errors.name }}</span>
      </div>

      <!-- Surname (Mandatory) -->
      <div>
        <label for="surname">
          <span class="required-asterisk">*</span> Surname:
        </label>
        <input type="text" id="surname" v-model="newUser.surname" />
        <span v-if="errors.surname">{{ errors.surname }}</span>
      </div>

      <!-- Gender (Mandatory) -->
      <div>
        <label for="gender">
          <span class="required-asterisk">*</span> Gender:
        </label>
        <select id="gender" v-model="newUser.gender" >
          <option value="">Select Gender</option>
          <option value="M">Male</option>
          <option value="F">Female</option>
        </select>
        <span v-if="errors.gender">{{ errors.gender }}</span>
      </div>

      <!-- Birthdate (Mandatory) -->
      <div>
        <label for="birthdate">
          <span class="required-asterisk">*</span> Birthdate:
        </label>
        <input type="date" id="birthdate" v-model="newUser.birthdate"  />
        <span v-if="errors.gender">{{ errors.birthdate }}</span>
      </div>

      <!-- Work Address (Non-Mandatory) -->
      <div>
        <label for="workAddress">Work Address:</label>
        <textarea id="workAddress" v-model="newUser.address.workAddress"></textarea>
        <span v-if="errors.workAddress">{{ errors.workAddress }}</span>
      </div>

      <!-- Home Address (Non-Mandatory) -->
      <div>
        <label for="homeAddress">Home Address:</label>
        <textarea id="homeAddress" v-model="newUser.address.homeAddress"></textarea>
        <span v-if="errors.homeAddress">{{ errors.homeAddress }}</span>
      </div>

      <!-- Submit Button -->
      <div>
        <button type="submit">Register</button>
      </div>
    </form>
  </div>
    </template>
  <script>
  import axios from "axios";

  export default {
    name: 'RegisterUser',
      data() {
        return {
          newUser: {  // New user data
          name: '',
          surname: '',
          gender: '',
          birthdate: '',
          address: {
              workAddress: '',
              homeAddress: ''
            }
          },
          errors: {},
        };
    },
      methods: {
        validateForm() {
          this.errors = {}; // clear previous errors
          // Validate name (required, only letters, numbers, dashes)
          if (!this.newUser.name) {
            this.errors.name = "Name is required";
          } else if (!/^[a-zA-Z0-9-]+$/.test(this.newUser.name)) {
            this.errors.name = "Only letters, numbers, and dashes are allowed in name";
          }

              // Validate surname (required, only letters, numbers, dashes)
          if (!this.newUser.surname) {
            this.errors.surname = "Surname is required";
          } else if (!/^[a-zA-Z0-9-]+$/.test(this.newUser.surname)) {
            this.errors.surname = "Only letters, numbers, and dashes are allowed in surname";
          }

          // Validate gender (required, must be M or F)
          if (!this.newUser.gender) {
            this.errors.gender = "Gender is required";
          } else if (!["M", "F"].includes(this.newUser.gender)) {
            this.errors.gender = "Gender must be 'M' or 'F'";
          }

          // Validate birthdate (required, must be a valid date and not in the future)
          if (!this.newUser.birthdate) {
            this.errors.birthdate = "Birthdate is required";
          } else if (isNaN(Date.parse(this.newUser.birthdate))) {
            this.errors.birthdate = "Birthdate must be a valid date";
          } else if (new Date(this.newUser.birthdate) > new Date()) {
            this.errors.birthdate = "Birthdate cannot be in the future";
          }

          // Validate addresses (allow empty or only letters, numbers, dashes, spaces)
          const addressRegex = /^[a-zA-Z0-9-\s]*$/;
          if (this.newUser.address.workAddress && !addressRegex.test(this.newUser.address.workAddress)) {
            this.errors.workAddress = "Work address can contain only letters, numbers, dashes, and spaces";
          }
          if (this.newUser.address.homeAddress && !addressRegex.test(this.newUser.address.homeAddress)) {
            this.errors.homeAddress = "Home address can contain only letters, numbers, dashes, and spaces";
          }

          /* returns true(1) if no errors */
          return Object.keys(this.errors).length === 0;

        },

      async createUser() {
         if (!this.validateForm()) {
        // There are validation errors — don't submit
           return;
         }

        try {
          // Send POST request to the backend to create the new user
          await axios.post("http://localhost:8090/api/users", this.newUser);
          

          // Reset the form fields
          this.newUser.name = '';
          this.newUser.surname = '';
          this.newUser.gender ='',
          this.newUser.birthdate ='',
          this.newUser.address.workAddress ="",
          this.newUser.address.homeAddress = "", 
            
          this.errors = {};

          alert("User created successfully!");
        } catch (error) {
          alert(error.response.data.message);
        }
      },
    }
  }
  </script>
  
  <style scoped>
  .registerForm{
    min-height: 100vh;
    padding: 2rem;
    background: rgb(255, 247, 237);
    display: flex;
    flex-direction: column;
    align-items: center;
  }

form {
  width: 300px;
  margin: auto;
}
label {
  display: block;
  margin-bottom: 5px;
}
input, select, textarea {
  width: 100%;
  margin-bottom: 10px;
  padding: 8px;
  font-size: 14px;
}
textarea {
  height: 100px;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  font-size: 16px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
span {
  font-size: 12px;
  color: red;
}
.required-asterisk {
  color: red;
  margin-left: 4px;
}

</style>

  