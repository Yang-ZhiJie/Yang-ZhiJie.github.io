<template>
  <div class="add">
    <a-divider>添加商品</a-divider>
    <a-form
      id="components-form-demo-normal-login"
      :form="form"
      class="login-form"
      @submit="handleSubmit"
    >
      <a-form-item class="forminput">
        <a-input
          v-decorator="[
          'name',
          { rules: [{ required: true, message: '输入商品名称' }] },
        ]"
          type="text"
          placeholder="商品名"
          style="margin-left:200px;"
        ></a-input>
      </a-form-item>
      <a-form-item>
        <a-input
          v-decorator="[
          'sort',
          { rules: [{ required: true, message: '输入数字类型商品排序' }] },
        ]"
          type="number"
          placeholder="商品排序"
          style="margin-left:200px;"
        ></a-input>
      </a-form-item>
      <a-form-item>
        <a-input
          v-decorator="[
          'content',
          { rules: [{ required: true, message: '输入商品介绍' }] },
        ]"
          type="text"
          placeholder="商品介绍"
          style="margin-left:200px;"
        ></a-input>
      </a-form-item>
      <a-form-item style="margin-left:200px;">
        <a-select defaultValue="请选择类别" style="width: 300px" @change="typeChange">
          <a-select-option v-for="(ty,k) in typelist" :key="k" :value="k">{{ty.name}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          html-type="submit"
          class="checkproduct"
          style="margin-left:280px;width:300px;"
        >监测商品是否存在</a-button>
      </a-form-item>
      <!-- 类别选项框 -->
      <a-divider>添加库存</a-divider>

      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        v-for="(ta,k) in typeattr2"
        :label="ta"
        :key="k"
      >
        <a-select
          class="typelist"
          v-if="ta != null"
          mode="tags"
          placeholder="Please select"
          style="width: 200px"
          @change="(e)=>handleGet(e,k)"
        ></a-select>
      </a-form-item>
      <a-form-item>
        <a-button class="borntype" @click="niu" style="margin-left:280px;width:300px;">点击生成</a-button>
      </a-form-item>
    </a-form>

    <!-- 自动生成库存表 -->
    <!-- <div :label-col="{ span: 8 }" :wrapper-col="{ span: 12 }"> -->
    <a-form :form="form2" v-if="autodata_rule" class="add-type" @submit="handler">
      <a-row style="margin-top: 20px" v-for="(item,index) in zum" :key="index">
        <a-col :span="3" style="text-align: center;">
          <div style="margin-top: 10px">{{item}}</div>
        </a-col>

        <a-col :span="4">
          <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 12 }" label="金额">
            <a-input-number
              v-decorator="[
          `goods_cost${index}`,
          {rules: [{ required: true, message: '请填写金额' }]}
        ]"
              :min="0"
              @change="(e)=>getcostlist(e,index,'goods_cost')"
            ></a-input-number>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 12 }" label="库存">
            <a-input-number
              v-decorator="[
          `goods_sku${index}`,
          {rules: [{ required: true, message: '请填写库存' }]}
        ]"
              :min="0"
              @change="(e)=>getcostlist(e,index,'goods_sku')"
            ></a-input-number>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item :label-col="{ span: 10 }" :wrapper-col="{ span: 12 }" label="成本价">
            <a-input-number
              v-decorator="[
          `goods_price${index}`,
          {rules: [{ required: true, message: '请填写成本价' }]}
        ]"
              :min="0"
              @change="(e)=>getcostlist(e,index,'goods_price')"
            ></a-input-number>
          </a-form-item>
        </a-col>
      </a-row>
      <a-form-item>
        <a-button type="primary" html-type="submit" class="add-type-button">录入库存</a-button>
      </a-form-item>
    </a-form>
    <!-- </div> -->
    <!-- 标签选项框 -->
    <a-divider>添加标签</a-divider>
    <a-form class="add-tag" :form="form3" @submit="inputchange" style="margin-left:280px;">
      <a-select defaultValue="请选择标签类型" style="width: 300px" @change="tagChange">
        <a-select-option v-for="(tl,k) in taglist" :key="k" :value="k">{{tl.name}}</a-select-option>
      </a-select>
      <!-- 保质期选择 -->
      <a-form-item v-if="tagId==0">
        <a-range-picker
          :showTime="{ format: 'HH:mm' }"
          format="YYYY-MM-DD"
          :placeholder="['生产日期', '过期时间']"
          @change="ontimeChange"
        />
      </a-form-item>
      <!-- 上传图片 -->
      <a-form-item v-if="tagId==2">
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

      <!-- 输入框 -->
      <a-form-item v-if="tagId==1">
        <a-input
          v-decorator="[
          'salecontent',
          { rules: [{ required: true, message: '输入促销宣传语' }] },
        ]"
          type="text"
          placeholder="促销语句"
        ></a-input>
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          html-type="submit"
          class="add-tags-button"
          style="margin-left:100px;"
        >添加标签</a-button>
      </a-form-item>
      <a-form-item>
        <a-button
          class="add-product-button"
          html-type="submit"
          @click="uploadpro"
          style="margin-right:100px;width:300px;"
        >点击确认,完成添加</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 8 }
};
const formTailLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 8, offset: 4 }
};
import qs from "qs";
// import Addtype from "@/components/addtype";
export default {
  //   components: {
  //     Addtype
  //   },
  data() {
    return {
      token: "",
      typelist: "",
      num: "",
      typeattr: [],
      typeattr2: [],
      formItemLayout,
      formTailLayout,
      arr: [],
      autodata_rule: false,
      zum: [],
      a: [],
      l: [],
      b: [],
      s: "",
      val: [],
      category_id: "",
      attr: [],
      taglist: [
        { id: 1, name: "保质期" },
        { id: 2, name: "促销宣传语" },
        { id: 3, name: "图片" }
      ],
      previewVisible: false,
      previewImage: "",
      fileList: [],
      tagId: -1,
      tagObj: "",
      skuData: []
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
    this.form2 = this.$form.createForm(this, { name: "add-type" });
    this.form3 = this.$form.createForm(this, { name: "add-tag" });
  },
  mounted() {
    this.getToken();
  },
  methods: {
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
    getcostlist(val, index, valtype) {
      switch (valtype) {
        case "goods_cost":
          this.val[index].goods_cost = val;
          break;
        case "goods_sku":
          this.val[index].goods_sku = val;

          break;
        case "goods_price":
          this.val[index].goods_price = val;

          break;

        default:
          break;
      }
      //   console.log(val);
    },
    niu() {
      this.set(this.arr, 0);
      for (let i = 0; i < this.zum.length; i++) {
        this.val.push({
          goods_cost: "",
          goods_sku: "",
          goods_price: ""
        });
      }
      this.autodata_rule = true;
    },
    set(arr, k) {
      for (let i = 0; i < arr[k].length; i++) {
        this.b[k] = this.typeattr2[k];
        this.a[k] = arr[k][i];
        if (arr[k + 1] != undefined && arr[k + 1] != null) {
          this.set(arr, k + 1);
        } else {
          for (let j = 0; j < this.a.length; j++) {
            this.s = this.b[j] + ":" + this.a[j] + " " + this.s;
            if (j == this.a.length - 1) {
              console.log(this.b);
              this.zum.push(this.s);
              this.s = "";

              console.log(this.attr);
            }
          }
          this.attr.push({
            attr1: this.a[0],
            attr2: this.a[1],
            attr3: this.a[2] == undefined ? "" : this.a[2]
          });
        }
      }
    },
    handleGet(e, k) {
      //   console.log(e);
      this.arr[k] = e;
    },

    handler(e) {
      // console.log(this.val);
      let skuList = this.val;
      let typeList = [];
      let goodstypeList = [];
      e.preventDefault();
      this.form2.validateFields((err, values) => {
        // console.log(values);
        if (skuList != null) {
          // console.log(skuList);
          for (const key in skuList) {
            // console.log(skuList[key]);
            let inserttype = {
              productId: "",
              originalPrice: skuList[key].goods_cost.toString(),
              price: skuList[key].goods_price.toString(),
              quantity: skuList[key].goods_sku.toString(),
              attr1: this.attr[key].attr1,
              attr2: this.attr[key].attr2,
              attr3: this.attr[key].attr3
            };
            this.skuData.push(inserttype);
            console.log(this.skuData);
          }
        }
      });
    },
    //获取标签提交内容
    inputchange(e) {
      e.preventDefault();

      this.form3.validateFields((err, values) => {
        if (!err) {
          switch (this.tag_id) {
            case 1:
              this.tagData = {
                productId: "",
                tagId: this.tag_id,
                value: this.productiondate
              };
              break;
            case 2:
              this.tagData = {
                productId: "",
                tagId: this.tag_id,
                value: values.salecontent
              };
              break;
            case 3:
              this.tagData = {
                productId: "",
                tagId: this.tag_id,
                value: this.Imgs
              };
              break;
            default:
              break;
          }
          console.log(this.tagData);
        }
      });
    },
    ontimeChange(value, dateString) {
      // console.log("Selected Time: ", value);
      console.log("Formatted Selected Time: ", dateString);
      for (const key in dateString) {
        this.productiondate = dateString[0] + "~" + dateString[1];
      }
      console.log(this.productiondate);
    },

    //获取商品添加的内容
    handleSubmit(e) {
      //获取input 框里的值
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          let data = values;
          //商品数据
          this.productData = {
            name: data.name,
            sort: data.sort,
            content: data.content,
            categoryId: this.category_id.toString()
          };
        }
      });
    },
    uploadpro() {
      // this.handleSubmit(e);
      // this.handler(e);
      // for (const p in this.skuData) {
      let AddPro = {
        product: this.productData,
        sku: this.skuData,
        productTag: this.tagData
      };
      console.log(AddPro);
      this.axios({
        url: this.url + "api/addproduct?token=" + this.token,
        method: "POST",
        data: JSON.stringify(AddPro),
        headers: { "Content-Type": "application/json" }
      }).then(res => {
        console.log(res);
      });
      // }
    },
    //获得选中的类别
    typeChange(e) {
      //   console.log(e);
      this.typeattr2 = this.typeattr[e];
      this.category_id = this.typelist[e].id;
      console.log(this.category_id);
    },
    //获取选中的标签
    tagChange(e) {
      // console.log(e);
      this.tagId = e;
      this.tag_id = this.taglist[e].id;
      console.log(this.tag_id);
    },
    //获取类别信息
    showType(token) {
      this.axios.get(this.url + "api/type/getall?token=" + token).then(res => {
        // console.log(res);
        this.typelist = res.data.data.data;
        // this.typeattr = res.data.data.data;

        this.typelist.forEach((value, key) => {
          //   console.log(key);
          let propertylist = JSON.parse(value.property);
          this.typeattr.push([
            propertylist.attr1,
            propertylist.attr2,
            propertylist.attr3
          ]);
        });
        console.log(this.typeattr);

        this.typeattr2 = this.typeattr[this.num];
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
        this.showType(token);
      }
    }
  }
};
</script>
<style scoped>
#components-form-demo-normal-login .login-form {
  max-width: 500px;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
.add-type-button {
  width: 200px;
  margin-left: 200px;
}
.add {
  width: 900px;
  margin: 0 auto;
}
.login-form input {
  width: 500px;
}
.typelist {
  /* padding-left: -300px; */
}
.checkproduct {
  /* position: absolute; */
  margin-left: 150px;
}
</style>