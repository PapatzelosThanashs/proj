<template>
  <div>
    
    <div class="userdetail" v-if="user">

      <table  class="user-table" >
           <thead>
                <tr>
                <th colspan="2">User Details</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><strong>Name</strong> </td>
                    <td @click="editField('name')" v-if="!editing.name">{{ user.name }}</td>
                    <td v-else><input type="text" v-model="user.name" @blur="saveField('name')"     @input="validateField('name')"/></td>
                    <span v-if="errors.name">{{ errors.name }}</span>
                </tr>
                <tr>
                    <td><strong>Surname</strong></td>
                    <td @click="editField('surname')" v-if="!editing.surname">{{ user.surname }}</td>
                    <td v-else><input type="text" v-model="user.surname" @blur="saveField('surname')"    @input="validateField('surname')"/></td>
                    <span v-if="errors.surname">{{ errors.surname }}</span>
                </tr>
                <tr>
                    <td><strong>Gender</strong></td>
                    <td @click="editField('gender')" v-if="!editing.gender">{{ user.gender }}</td>
                        <td v-else>
                        <select v-model="user.gender" @blur="saveField('gender')"    @input="validateField('gender')">
                            <option value="M">M</option>
                            <option value="F">F</option>
                        </select>
                    </td>
                    <span v-if="errors.gender">{{ errors.gender }}</span>
                </tr>
                <tr>
                    <td><strong>Birthdate</strong> </td>
                    <td @click="editField('birthdate')" v-if="!editing.birthdate">{{ user.birthdate }}</td>
                    <td v-else><input type="date" v-model="user.birthdate" @blur="saveField('birthdate')"   @input="validateField('birthdate')" ></td>
                    <span v-if="errors.birthdate">{{ errors.birthdate }}</span>
                    </tr>
                <tr>
                    <td><strong>Work Address</strong></td>
                    <td @click="editField('workAddress')" v-if="!editing['workAddress']">{{ user.address.workAddress }}</td>
                    <td v-else><input type="text" v-model="user.address.workAddress" @blur="saveField('workAddress')"  @input="validateField('workAddress')"/></td>
                    <span v-if="errors.workAddress">{{ errors.workAddress }}</span>
                </tr>
                <tr>
                    <td><strong>Home Address</strong></td>
                    <td @click="editField('homeAddress')" v-if="!editing['homeAddress']">{{ user.address.homeAddress }}</td>
                    <td v-else><input type="text" v-model="user.address.homeAddress" @blur="saveField('homeAddress')"  @input="validateField('homeAddress')"/></td>
                    <span v-if="errors.homeAddress">{{ errors.homeAddress }}</span>
                </tr>
            </tbody>
        </table>

      
    </div>
    <div v-else>
      <p>User not found.</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserDetail",
  data() {
    return {
      user: null,
      errors: {},
      editing: {
      name: false,
      surname: false,
      gender: false,
      version: false,
      birthdate: false,
      workAddress: false,
      homeAddress: false
      }
    };
  },
  async mounted() {
    await this.loadUser();
  },
    methods: {
    // Set the field to editing mode
    editField(field) {
      this.editing[field] = true;  // Directly assign to the editing object
    },

    // Save the edited field and exit editing mode
    async saveField(field) {
    
     
      if (this.errors[field] ) return;

      this.editing[field] = false;  // Exit editing mode for the specific field
      if (confirm("Are you sure you want to update this user?")) {
      try {
        // Make PATCH request to backend API to update the user details
        await axios.patch(`http://backend:8090/api/users/${this.user.id}`, this.user);
        await this.loadUser();  // Reload latest data from database
        alert("User updated successfully!");
      } catch (error) {
        alert(error.response.data.message);
      }
    }
    },
    async loadUser() {
      
      const userId = this.$route.params.id;
        try {
          const response = await axios.get(`http://backend:8090/api/users/${userId}`);
          this.user = response.data;
        } catch (error) {
         //alert("User not found or error occurred.");
         alert(error.response.data.message);
        }
      },
     
      validateField(field) {

        let value;
        
        if(field === "workAddress" || field === "homeAddress"){
          value = this.user.address[field];
        }else{
          value = this.user[field];
        }
          
        
          delete this.errors[field]; 


          if (field === 'name' || field === 'surname') {
            if (!value) {
              this.errors[field] = "This field is required";
            } else if (!/^[a-zA-Z0-9-]+$/.test(value)) {
              this.errors[field] = "Only letters, numbers, and dashes allowed";
            }
          }

          if (field === 'gender') {
            if (!value) {
              this.errors[field] = "Gender is required";
            } else if (!["M", "F"].includes(value)) {
              this.errors[field] = "Gender must be 'M' or 'F'";
            }
          }

          if (field === 'birthdate') {
            if (!value) {
              this.errors.birthdate = "Birthdate is required";
            } else if (isNaN(Date.parse(value))) {
              this.errors.birthdate = "Invalid date";
            } else if (new Date(value) > new Date()) {
              this.errors.birthdate = "Birthdate cannot be in the future";
            }
          }
       

          if (field === 'workAddress' || field === 'homeAddress') {
               if (!/^[a-zA-Z0-9- ]+$/.test(value)) {
              this.errors[field] = "Only letters, numbers, and dashes allowed";
            }
          }
        }


    }
};

</script>

<style scoped>
h2 {
  text-align: center;
}
p {
  font-size: 1.1em;
}

.user-table {
  border-collapse: collapse;
  text-align: left;

}
.user-table th,
.user-table td {
  border: 2px solid #000000;
  padding: 8px;
}
.userdetail{
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0px;
    
    min-height: 100vh;
    padding: 2rem;
    background: rgb(255, 247, 237);
   
}

</style>
