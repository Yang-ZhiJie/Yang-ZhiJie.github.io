<template>
  <a-form class="add_nav" :form="form" @submit="handleSubmit" style="margin-left:280px;">
    <a-form-item style="margin-left:200px;">
      <a-select defaultValue="请选择标签类型" style="width: 300px" @change="navChange">
        <a-select-option v-for="(tl,k) in navList" :key="k" :value="k">{{tl.name}}</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item style="margin-left:200px;width:300px;">
      <a-input
        v-decorator="[
          'title',
          { rules: [{ required: true, message: '输入导航名' }] },
        ]"
        type="text"
        placeholder="导航名"
      ></a-input>
    </a-form-item>

    <!-- 上传图片 -->
    <a-form-item style="margin-left:200px;">
      <a-upload
        listType="picture-card"
        :fileList="fileList"
        @preview="handlePreview"
        @change="handleChange"
        :customRequest="customRequest"
      >
        <div>
          <a-icon type="plus" />
          <div class="ant-upload-text">Upload</div>
        </div>
      </a-upload>
      <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
        <img alt="example" style="width: 100%" :src="previewImage" />
      </a-modal>
    </a-form-item>

    <a-form-item style="margin-left:200px;">
      <a-select defaultValue="请选择链接类型" style="width: 300px" @change="targetChange">
        <a-select-option v-for="(tl,k) in targetList" :key="k" :value="k">{{tl.name}}</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item style="margin-left:200px;width:300px;" v-if="show&&linkId==1">
      <a-select defaultValue="请选择链接目标" style="width: 300px" @change="target2Change">
        <a-select-option v-for="(tl,k1) in typeList" :key="k1" :value="k1">{{tl.name}}</a-select-option>
      </a-select>
    </a-form-item>

    <a-form-item>
      <a-button
        class="add-product-button"
        html-type="submit"
        style="margin-left:200px;width:300px;"
      >点击确认,完成添加</a-button>
    </a-form-item>
  </a-form>
</template>
<script>
import qs from "qs";

export default {
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "add_nav" });
  },
  data() {
    return {
      navList: [
        { id: 1, name: "顶部导航" },
        { id: 2, name: "轮播图" },
        { id: 3, name: "图标icon" },
        { id: 4, name: "四张大图" }
      ],
      targetList: [
        { id: 1, name: "商品分类" },
        { id: 2, name: "商品购买" },
        { id: 3, name: "活动页面" },
        { id: 4, name: "店铺页面" }
      ],
      previewVisible: false,
      previewImage: "",
      fileList: [],
      show: false,
      linkId: true,
      typeList: []
    };
  },
  mounted() {
    this.getToken();
  },
  methods: {
    //获取选中的标签
    navChange(e) {
      console.log(e);
      this.navId = e;
      this.nav_id = this.navList[e].id;
      console.log(this.nav_id);
    },
    targetChange(e) {
      console.log(e);
      this.targetId = e;
      this.target_id = this.targetList[e].id;
      switch (this.target_id) {
        case 1:
          this.show = true;
          this.axios
            .get(this.url + "api/type/getall?token=" + this.token)
            .then(res => {
              if (res.data.data.data != null) {
                this.typeList = [];
                this.typeList = res.data.data.data;
              } else {
                alert("服务器繁忙,请稍后重试");
              }
              //   console.log(res.data.data.data);
            });
          break;
        case 2:
          this.show = true;
          this.axios
            .get(this.url + "api/get/allproducts?token=" + this.token)
            .then(res => {
              console.log(res);
              if (res.data.data.data != null) {
                this.typeList = [];
                this.typeList = res.data.data.data;
              } else {
                alert("服务器繁忙,请稍后重试");
              }
            });

          break;
        case 3:
          //   this.show = true;
          alert("该功能还在开发中...");

          break;
        case 4:
          //   this.show = true;
          alert("该功能还在开发中...");

          break;
        default:
          break;
      }
      console.log(this.target_id);
    },
    target2Change(e) {
      console.log(e);
      //   this.linkTarget = e;
      this.link_target = this.typeList[e].id;
    },
    //图片上传
    //获取图片
    handleCancel() {
      this.previewVisible = false;
    },
    handlePreview(file) {
      console.log(file);
      this.previewImage = file.url;
      this.previewVisible = true;
    },
    handleChange({ fileList }) {
      this.fileList = fileList;
    },
    customRequest(file) {
      console.log(file);
      const formdata = new FormData();
      formdata.append("file", file.file);
      // console.log(formdata);
      this.uploadimg(formdata);
    },
    uploadimg(file) {
      let config = {
        headers: { "Content-Type": "multipart/form-data" }
      };
      this.axios
        .post(this.url + "api/uploadImg?token=" + this.token, file)

        .then(res => {
          console.log(res);
          if (res.data != null) {
            this.Imgs = res.data;
            this.fileList = [];
            console.log(this.fileList);
            this.fileList.push({
              uid: "0",
              status: "done",
              name: "img",
              url: "http://localhost:8080/" + this.Imgs + "?token=" + this.token
            });
          }
        });
    },
    //获取商品添加的内容
    handleSubmit(e) {
      //获取input 框里的值
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          let data = values;
          console.log(this.linkTargetId);
          if (this.linkTargetId != null) {
            this.LINKID = this.linkTargetId;
          } else {
            this.LINKID = this.link_target;
          }
          //商品数据
          let navData = {
            positionId: this.nav_id.toString(),
            titleName: data.title,
            picture: this.Imgs,
            linkType: this.target_id.toString(),
            linkTarget: this.LINKID
          };

          console.log(navData);
          if (navData != null) {
            this.axios
              .post(
                this.url + "api/addnav?token=" + this.token,
                qs.stringify(navData)
              )
              .then(res => {
                console.log(res);
                if (res.status == 200) {
                  this.$router.push("nav");
                }
              });
          } else {
            alert("数据异常,稍后重试");
          }
        }
      });
    },
    //判断有没有token
    getToken() {
      //判断有没有token
      let token = localStorage.getItem("token");
      if (token == null) {
        alert("请先登录");
        this.$router.push("/");
      } else {
        this.token = token;
        this.getlinkTarget();
        // this.showType(token);
      }
    },
    //判断有没有目标链接
    async getlinkTarget() {
      this.linkTargetId = this.$route.params.productId;
      console.log("hahha" + this.linkTargetId);
      if (this.linkTargetId != undefined) {
        this.linkId = 0;
      } else {
        console.log("没有链接目标");
        this.linkId = 1;
      }
    }
  }
};
</script>