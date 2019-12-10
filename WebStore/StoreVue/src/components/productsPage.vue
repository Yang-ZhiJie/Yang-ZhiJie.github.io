<template>
  <a-table :columns="columns" :dataSource="data" bordered rowKey="id">
    <template
      v-for="col in ['name', 'saleNum', 'content']"
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
    <template slot="updateProduct" slot-scope="text, record, index">
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
    <template slot="getNav" slot-scope="text, record, index">
      <div class="editable-row-operations">
        <span>
          <a @click="() => goNav(record.id)">查看导航</a>
        </span>
      </div>
    </template>
    <template slot="getSku" slot-scope="text, record, index">
      <div class="editable-row-operations">
        <span>
          <a @click="() => gosku(record.id)">查看库存</a>
        </span>
      </div>
    </template>
  </a-table>
</template>

<script>
import qs from "qs";
const columns = [
  {
    title: "商品名",
    dataIndex: "name",
    // width: "25%",
    scopedSlots: { customRender: "name" }
  },
  {
    title: "销量",
    dataIndex: "saleNum",
    // width: "15%",
    scopedSlots: { customRender: "saleNum" }
  },
  {
    title: "商品详情",
    dataIndex: "content",
    // width: "40%",
    scopedSlots: { customRender: "content" }
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
    title: "库存信息",
    dataIndex: "getSku",
    scopedSlots: { customRender: "getSku" }
  },
  {
    title: "导航信息",
    dataIndex: "getNav",
    scopedSlots: { customRender: "getNav" }
  },
  {
    title: "修改操作",
    dataIndex: "updateProduct",
    scopedSlots: { customRender: "updateProduct" }
  }
];
const data = [];
for (let i = 0; i < data.length; i++) {
  data.push({
    // key: i.toString(),
    id: "",
    name: "",
    sale_num: "",
    content: "",
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
    //跳转库存页面
    gosku(target) {
      console.log(target);
      if (target != null) {
        this.productId = target;
        this.$router.push({
          path: "skus",
          name: "skus",
          params: { productId: this.productId }
        });
      }
    },
    goNav(navId) {
      console.log(navId);
      if (navId != null) {
        this.productId = navId;
        this.$router.push({
          path: "nav",
          name: "nav",
          params: { productId: this.productId }
        });
      }
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
        const product = {
          id: target.id,
          name: target.name,
          content: target.content,
          saleNum: target.saleNum,
          status: target.status
        };
        console.log(product);
        const token = this.token;
        this.axios
          .post(this.url + `api/update?token=${token}`, qs.stringify(product))
          .then(res => {
            console.log(res);
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
      if (token != null) {
        this.token = token;
        this.getProducts(token);
      } else {
        alert("请先登录");
        this.$router.push("/");
      }
    },

    getProducts(token) {
      if (token != null) {
        this.axios
          .get(this.url + "api/get/allproducts?token=" + token)
          .then(res => {
            // console.log(res);
            // console.log(res.data.status);
            if (res.data.status == false) {
              alert("登录失效，请重新登录");
              this.$router.push("/");
            } else {
              this.data = res.data.data.data;
            }
          });
      } else {
        alert("登录失效，请重新登录");
        this.$router.push("/");
      }
    }
  }
};
</script>
<style scoped>
/* .editable-row-operations {
  display: inline-block;
} */
.editable-row-operations a {
  margin-right: 8px;
  /* margin: 0 auto; */
}
</style>
