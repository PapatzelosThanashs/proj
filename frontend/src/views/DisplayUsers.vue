<template>
    <div class="displayusers">
      <div class="refresh-container">
        <button class="refresh-button" @click="getUsers()">🔄 Refresh Users</button>
      </div>
        <table class="user-table">
            <thead>
                <tr>
                <th>NAME</th>
                <th>SURNAME</th>
                </tr>
            </thead>
            <tbody>
                <tr class="user-row" v-for="user in users" :key="user.id"  @click="userDetails(user.id)">
                <td>{{ user.name }}</td>
                <td>{{ user.surname }}</td>
                <td><button class="delete-button" @click.stop="deleteUser(user.id)">🗑️ Delete User</button></td>
                </tr>
            </tbody>
        </table>
    </div>
    </template>
  <script>

import axios from "axios";

  export default {
    name: 'DisplayUsers',
    data(){
    return{ 
      users:[]
    };
  },
    methods: {
        async getUsers() {
        try {
            const response = await axios.get("http://localhost:8090/api/users");
           
            this.users = response.data;

        } catch (error) {
            alert(error.response.data.message);
        }
        },
        async deleteUser(userId) {
          if (confirm("Are you sure you want to delete this user?")) {
            try {
              await axios.delete(`http://localhost:8090/api/users/${userId}`);
              this.users = this.users.filter(user => user.id !== userId);
              alert("User successfully deleted");
            } catch (error) {
              alert(error.response.data.message);
            }
        }
    },
      async userDetails(userId){
        window.open(`/user/${userId}`, "_blank");

    }
  },
    created(){
        this.getUsers();
    }
  }
  </script>
  
  <style scoped>
  .displayusers {
    min-height: 100vh;
    padding: 2rem;
    background: rgb(255, 247, 237);
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .user-table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #ffffff;
  font-family: Arial, sans-serif;
}

.user-table th,
.user-table td {
  padding: 12px 15px;
  border: 1px solid #0f0101;
  text-align: left;
}

.user-table thead {
  background-color: #464545;
  color: white;
}

.user-row {
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.user-row:hover {
  background-color: #e69e63;
}

.delete-button {
  background-color: #c2574c;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

.delete-button:hover {
  background-color: #e6331f;
}

.refresh-button {
  background-color: #77c24c;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  margin: 0.5rem;
  cursor: pointer;
  font-size: 1rem;
}

.refresh-button:hover {
  background-color: #64b635;
}

.refresh-container {
  width: 100%;
  display: flex;
  justify-content: flex-start; /* Align left */
  margin-bottom: 1rem; /* Space between button and table !.some changes to trigger pipeline!*/
}



  </style>
  