<template>
  <div id="register" class="text-center">
    <form @submit.prevent="register">
      <h2><b>Create Account</b></h2>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input
          type="text"
          id="username"
          v-model="user.username"
          required
          autofocus
        />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input
          type="password"
          id="confirmPassword"
          v-model="user.confirmPassword"
          required
        />
      </div>
      <b-button style="background-color: #60233f" type="submit"
        >Create Account</b-button
      >
      <p>
        <router-link :to="{ name: 'login' }"
          >Already have an account? Log in.</router-link
        >
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}
form {
  color: #000;
  background: #ffc;
  display: block;
  height: 20em;
  width: 20em;
  padding: 1em;
  margin: 1em;
  box-shadow: 5px 5px 7px rgba(33, 33, 33, 0.7);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  font-family: courier, monospace;
}

div {
  display: flex;
  justify-content: center;
}
</style>
