<template>
  <div class="app">
    <a-form
      id="components-form-demo-normal-login"
      :form="form"
      class="login-form"
      @submit="handleSubmit"
    >
      <a-form-item>
        <a-input
          v-decorator="[
          'username',
          { rules: [{ required: true, message: 'Please input your username!' }] },
        ]"
          placeholder="Username"
        >
          <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-input
          v-decorator="[
          'password',
          { rules: [{ required: true, message: 'Please input your Password!' }] },
        ]"
          type="password"
          placeholder="Password"
        >
          <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-checkbox
          v-decorator="[
          'remember',
          {
            valuePropName: 'checked',
            initialValue: true,
          },
        ]"
        >Remember me</a-checkbox>
        <a class="login-form-forgot" href>Forgot password</a>
        <a-button type="primary" html-type="submit" class="login-form-button">Log in</a-button>Or
        <a href>register now!</a>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import qs from "qs";
export default {
  data() {
    return {
      username: "",
      password: ""
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
  },
  methods: {
    handleSubmit(e) {
      //获取input 框里的值
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          //登录
          // login(data){
          let data = qs.stringify({
            username: values.username,
            password: values.password
          });
          localStorage.setItem("adminName", values.username);
          this.axios.post(this.url + "api/user/setToken", data).then(res => {
            localStorage.setItem("token", res.data.data[0].token);
            console.log(res.data.data[0].token);
            console.log(res);
            if (res.data.data[0].token != null) {
              console.log(123);
              alert("登录成功");
              this.$router.push("/about");
            }
          });
          // console.log(values.username), console.log(values.password)
        }
      });
    }
  }
};
</script>
<style scoped>
#components-form-demo-normal-login .login-form {
  max-width: 300px;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
.app {
  width: 400px;
  margin: 0 auto;
}
</style>