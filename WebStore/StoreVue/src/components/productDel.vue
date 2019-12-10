<template>
  <div>
    <a-table bordered :dataSource="dataSource" :columns="columns" rowKey="id">
      <template slot="deleteProduct" slot-scope="text, record">
        <a-popconfirm
          v-if="dataSource.length"
          title="Sure to delete?"
          @confirm="() => onDelete(record.id)"
        >
          <a href="javascript:;">删除商品</a>
        </a-popconfirm>
      </template>
    </a-table>
  </div>
</template>
<script>
import EditableCell from "./EditableCell";
/*
 * EditableCell Code https://github.com/vueComponent/ant-design-vue/blob/master/components/table/demo/EditableCell.vue
 */
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
    title: "删除操作",
    dataIndex: "deleteProduct",
    scopedSlots: { customRender: "deleteProduct" }
  }
];
const dataSource = [];
for (let i = 0; i < dataSource.length; i++) {
  dataSource.push({
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
  components: {
    EditableCell
  },

  data() {
    return {
      dataSource,
      columns,
      token: ""
    };
  },
  mounted() {
    this.getToken();
  },
  methods: {
    onCellChange(key, dataIndex, value) {
      const dataSource = [...this.dataSource];
      const target = dataSource.find(item => item.id === key);
      if (target) {
        target[dataIndex] = value;
        this.dataSource = dataSource;
      }
    },
    onDelete(key) {
      //   console.log(key);
      const id = key;
      const token = this.token;
      const dataSource = [...this.dataSource];
      this.dataSource = dataSource.filter(item => item.id !== key);
      this.axios.post(this.url + `api/del/${id}?token=${token}`).then(res => {
        console.log(res);
      });
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
      this.axios
        .get(this.url + "api/get/allproducts?token=" + token)
        .then(res => {
          console.log(res);
          this.dataSource = res.data.data.data;
        });
    }
  }
};
</script>
<style>
.editable-cell {
  position: relative;
}

.editable-cell-input-wrapper,
.editable-cell-text-wrapper {
  padding-right: 24px;
}

.editable-cell-text-wrapper {
  padding: 5px 24px 5px 5px;
}

.editable-cell-icon,
.editable-cell-icon-check {
  position: absolute;
  right: 0;
  width: 20px;
  cursor: pointer;
}

.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 28px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}

.editable-add-btn {
  margin-bottom: 8px;
}
</style>
