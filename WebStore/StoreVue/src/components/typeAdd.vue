<template>
  <div class="add">
    <a-form
      id="components-form-demo-normal-login"
      :form="form"
      class="login-form"
      @submit="handleSubmit"
    >
      <a-form-item>
        <a-input
          v-decorator="[
          'name',
          { rules: [{ required: true, message: '输入商品名称' }] },
        ]"
          type="text"
          placeholder="类别名"
        ></a-input>
      </a-form-item>
      <a-form-item>
        <div class="components-input-demo-size">
          <a-input
            v-decorator="[
          'attr1',
          { rules: [{ required: false, message: '输入属性名称' }] },
        ]"
            size="large"
            placeholder="类别属性1"
          />
          <a-input
            v-decorator="[
          'attr2',
          { rules: [{ required: false, message: '输入属性名称' }] },
        ]"
            size="large"
            placeholder="类别属性2"
          />
          <a-input
            v-decorator="[
          'attr3',
          { rules: [{ required: false, message: '输入属性名称' }] },
        ]"
            size="large"
            placeholder="类别属性3"
          />
        </div>
      </a-form-item>
      <a-form-item>
        <a-input
          v-decorator="[
          'sort',
          { rules: [{ required: true, message: '输入数字类型商品排序' }] },
        ]"
          type="number"
          placeholder="商品排序"
        ></a-input>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" class="login-form-button">点击添加</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import qs from "qs";
export default {
  data() {
    return {
      token: ""
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
  },
  mounted() {
    this.getToken();
  },
  methods: {
    handleSubmit(e) {
      //获取input 框里的值
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);

          //   let data = qs.stringify(values);
          //   console.log(data);
          let property = JSON.stringify({
            attr1: values.attr1,
            attr2: values.attr2,
            attr3: values.attr3
          });
          //   let name = JSON.stringify({ name: values.name });
          //   let sort = JSON.stringify({ sort: values.sort });
          //   console.log(name);
          //   console.log(sort);
          //   console.log(property);
          let data = qs.stringify({
            name: values.name,
            property,
            sort: values.sort
          });
          console.log(data);
          let token = this.token;
          this.axios
            .post(this.url + "api/addtype?token=" + token, data)
            .then(res => {
              console.log(res.data);
              console.log(res);
              if (res.data.status != false) {
                console.log(123);
                alert("添加成功");

                this.$router.go(0);
              } else {
                alert("类别添加失败");
              }
            });
        }
      });
    },
    //判断有没有token
    getToken() {
      //判断有没有token
      this.token = localStorage.getItem("token");
      if (this.token == null) {
        alert("请先登录");
        this.$router.push("/");
      }
    }
  }
};
</script>
<style scoped>
#components-form-demo-normal-login .login-form {
  max-width: 100px;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
.components-input-demo-size .ant-input {
  width: 100px;
  margin: 0 8px 8px 0;
}
.add {
  width: 400px;
  margin-left: 28%;
}
</style> 