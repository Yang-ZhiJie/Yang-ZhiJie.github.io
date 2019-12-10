<template>
  <div class="add">
    <a-form id="components-form-demo-normal-login" :form="form" class="login-form">
      <!-- @submit="handleSubmit" -->
      <a-form-item>
        <a-select defaultValue="请选择类别" style="width: 300px" @change="typeChange">
          <a-select-option v-for="(ty,k) in typelist" :key="k" :value="k">{{ty.name}}</a-select-option>
        </a-select>
      </a-form-item>

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
        <a-button class="borntype" @click="niu">点击生成</a-button>
      </a-form-item>
    </a-form>

    <!-- 自动生成库存表 -->
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

export default {
  data() {
    return {
      token: "",
      typelist: "",
      typeattr: [],
      typeattr2: [],
      formItemLayout,
      formTailLayout,
      autodata_rule: false,
      arr: [],
      zum: [],
      a: [],
      l: [],
      b: [],
      s: "",
      val: [],
      category_id: "",
      attr: []
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
    this.form2 = this.$form.createForm(this, { name: "add-type" });
  },
  mounted() {
    this.getToken();
  },
  methods: {
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
      console.log(this.val);
      let skuList = this.val;
      let typeList = [];
      let goodstypeList = [];
      e.preventDefault();
      this.form2.validateFields((err, values) => {
        // console.log(values);
        if (skuList != null) {
          for (const key in skuList) {
            console.log(skuList[key]);
            let inserttype = {
              productId: this.productId.toString(),
              original_price: skuList[key].goods_cost.toString(),
              price: skuList[key].goods_price.toString(),
              quantity: skuList[key].goods_sku.toString(),
              attr1: this.attr[key].attr1,
              attr2: this.attr[key].attr2,
              attr3: this.attr[key].attr3
            };
            goodstypeList.push(inserttype);
            console.log(goodstypeList);
          }
          for (let f in goodstypeList) {
            let data = qs.stringify({
              productId: goodstypeList[f].productId,
              originalPrice: goodstypeList[f].original_price,
              price: goodstypeList[f].price,
              quantity: goodstypeList[f].quantity,
              attr1: goodstypeList[f].attr1,
              attr2: goodstypeList[f].attr2,
              attr3: goodstypeList[f].attr3
            });
            console.log(data);
            let token = this.token;
            if (data != null) {
              this.axios
                .post(this.url + "api/add/sku?token=" + token, data)
                .then(res => {
                  console.log(res);
                  if (res.data.data.status != false) {
                    alert("添加成功");
                    this.$router.go(-1);
                  } else {
                    this.$notification.open({
                      message: "添加失败",
                      description: "服务器繁忙,请稍后重试"
                      //   onClick: () => {
                      //     console.log("Notification Clicked!");
                      //   }
                    });
                  }
                });
            }
          }
        }
      });
    },
    //获得选中的类别
    typeChange(e) {
      //   console.log(e);
      this.typeattr2 = this.typeattr[e];
      this.category_id = this.typelist[e].id;
      console.log(this.category_id);
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
      this.productId = this.$route.params.productId;
      if (this.productId == undefined) {
        this.$notification.open({
          message: "添加失败",
          description: "服务器繁忙,请稍后重试",
          onClick: () => {
            console.log("Notification Clicked!");
            this.$router.push("/about");
          }
        });
      }
      console.log(this.productId);
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
</script>>
