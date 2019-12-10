<template>
  <a-table :columns="columns" :dataSource="data" bordered rowKey="id">
    <template
      v-for="col in ['originalPrice','price','quantity']"
      :slot="col"
      slot-scope="text, record, index"
    >
      <div :key="col">
        <a-input
          v-if="record.editable"
          style="margin: -5px 0"
          :value="text"
          @change="e => handleChange(e.target.value, record.id, col)"
        />
        <template v-else>{{text}}</template>
      </div>
    </template>
    <template slot="updateSku" slot-scope="text, record, index">
      <div class="editable-row-operations">
        <span v-if="record.editable">
          <a @click="() => save(record.id)">修改</a>
          <a-popconfirm title="确认取消?" @confirm="() => cancel(record.id)">
            <a>取消</a>
          </a-popconfirm>
        </span>
        <span v-else>
          <a @click="() => edit(record.id)">修改</a>
        </span>
      </div>
    </template>
    <template slot="deleteSku" slot-scope="text, record,index">
      <a-popconfirm title="Sure to delete?" @confirm="() => onDelete(record.id)">
        <a href="javascript:;">删除库存</a>
      </a-popconfirm>
    </template>
  </a-table>
</template>

<script>
import qs from "qs";
import { get } from "http";
const columns = [
  {
    title: "成本价",
    dataIndex: "originalPrice",
    // width: "25%",
    scopedSlots: { customRender: "originalPrice" }
  },
  {
    title: "售价",
    dataIndex: "price",
    // width: "25%",
    scopedSlots: { customRender: "price" }
  },
  {
    title: "类别属性",
    dataIndex: "attr1",
    scopedSlots: { customRender: "attr1" }
  },
  {
    title: "类别属性",
    dataIndex: "attr2",
    scopedSlots: { customRender: "attr2" }
  },
  {
    title: "类别属性",
    dataIndex: "attr3",
    scopedSlots: { customRender: "attr3" }
  },
  {
    title: "商品库存",
    dataIndex: "quantity",
    // width: "40%",
    scopedSlots: { customRender: "quantity" }
  },
  {
    title: "创建时间",
    key: "createdAt",
    dataIndex: "createdAt",
    scopedSlots: { customRender: "createdAt" }
  },
  {
    title: "修改时间",
    key: "updatedAt",
    dataIndex: "updatedAt",
    scopedSlots: { customRender: "updatedAt" }
  },
  {
    title: "修改操作",
    dataIndex: "updateSku",
    scopedSlots: { customRender: "updateSku" }
  },
  {
    title: "删除操作",
    dataIndex: "deleteSku",
    scopedSlots: { customRender: "deleteSku" }
  }
];
const data = [];
for (let i = 0; i < data.length; i++) {
  data.push({
    // key: i.toString(),
    id: "",
    originalPirce: "",
    price: "",
    attr1: "",
    attr2: "",
    attr3: "",
    quantity: "",
    createdAt: "",
    updatedAt: ""
  });
}
export default {
  data() {
    this.cacheData = data.map(item => ({ ...item }));
    return {
      data,
      columns,
      token: ""
    };
  },
  mounted() {
    this.getToken();
  },
  methods: {
    //删除操作
    onDelete(key) {
      console.log(key);
      let id = key;
      let token = this.token;
      let dataSource = [...this.data];
      this.data = dataSource.filter(item => item.id !== key);
      this.axios
        .post(this.url + `api/delsku/${id}?token=${token}`)
        .then(res => {
          console.log(res);
          console.log(res.data);
          //   console.log(thing);
          if (res.data.status != true) {
            alert("登录过期,重新登录");
            this.$router.push("/");
          }
        });
    },
    handleChange(value, key, column) {
      const newData = [...this.data];
      const target = newData.filter(item => key === item.id)[0];
      // console.log(target);

      // console.log(key);
      if (target) {
        target[column] = value;
        this.data = newData;
      }
    },
    //修改
    edit(key) {
      const newData = [...this.data];
      const target = newData.filter(item => key === item.id)[0];
      if (target) {
        target.editable = true;
        this.data = newData;
      }
    },
    //保存
    save(key) {
      const newData = [...this.data];
      const target = newData.filter(item => key === item.id)[0];
      if (target) {
        delete target.editable;
        this.data = newData;
        this.cacheData = newData.map(item => ({ ...item }));

        let skuup = qs.stringify({
          id: target.id,
          originalPrice: target.originalPrice,
          price: target.price,
          quantity: target.quantity
        });
        console.log(skuup);
        const token = this.token;
        this.axios
          .post(this.url + `/api/updatesku?token=${token}`, skuup)
          .then(res => {
            console.log(res);
            if (res.data.status == true) {
              this.$notification.open({
                message: "库存修改成功",
                description: "请重新选择商品",

                onClick: () => {
                  console.log("Notification Clicked!");
                  // this.$router.push("/about");
                }
              });
            }
          });
      }
    },
    //取消
    cancel(key) {
      const newData = [...this.data];
      const target = newData.filter(item => key === item.id)[0];
      if (target) {
        Object.assign(
          target,
          this.cacheData.filter(item => key === item.id)[0]
        );
        delete target.editable;
        this.data = newData;
      }
    },
    //判断有没有token
    getToken() {
      //判断有没有token
      let token = localStorage.getItem("token");

      //   console.log(productId);
      if (token != null) {
        this.token = token;
        this.getId();
        this.getSku(token);
      } else {
        alert("请先登录");
        this.$router.push("/");
      }
    },
    async getId() {
      this.productId = this.$route.params.productId;
      //   console.log(this.productId);
      if (this.productId == undefined) {
        this.$notification.open({
          message: "数据加载失败",
          description: "请重新选择商品"
        });
        this.goback();
      }
    },
    goback() {
      this.$router.push("/about");
    },
    getSku(token) {
      let id = this.productId;
      console.log(id);
      if (token != null) {
        if (id != undefined) {
          this.axios
            .get(
              this.url + "api/sku/getone?product_id=" + id + "&token=" + token
            )
            .then(res => {
              //   console.log(res);
              if (res.data.status == true) {
                this.data = res.data.data.data;
              } else {
                alert("登录失效，请重新登录");
                this.$router.push("/");
              }
            });
        }
      } else {
        alert("登录失效，请重新登录");
        this.$router.push("/");
      }
    }
  }
};
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>
