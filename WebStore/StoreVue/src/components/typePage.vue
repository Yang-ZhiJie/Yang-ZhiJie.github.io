<template>
  <a-table :columns="columns" :dataSource="data" bordered rowKey="id">
    <template
      v-for="col in ['name', 'attr1', 'attr2','attr3','sort']"
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
  </a-table>
</template>

<script>
import qs from "qs";
const columns = [
  {
    title: "类别名",
    dataIndex: "name",
    // width: "25%",
    scopedSlots: { customRender: "name" }
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
    title: "类别排序",
    dataIndex: "sort",
    // width: "40%",
    scopedSlots: { customRender: "sort" }
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
    sort: "",
    attr1: "",
    attr2: "",
    attr3: "",
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
        let property = JSON.stringify({
          attr1: target.attr1,
          attr2: target.attr2,
          attr3: target.attr3
        });
        let typeup = qs.stringify({
          id: target.id,
          name: target.name,
          property,
          sort: target.sort
        });
        console.log(typeup);
        const token = this.token;
        this.axios
          .post(this.url + `api/update/type?token=${token}`, typeup)
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
          .get(this.url + "api/type/getall?token=" + token)
          .then(res => {
            console.log(res);

            if (res.data.status == false) {
              alert("登录失效，请重新登录");
              this.$router.push("/");
            } else {
              this.data = res.data.data.data;
              this.data.forEach((value, key) => {
                console.log(key);
                let propertylist = JSON.parse(value.property);
                console.log(propertylist);
                this.data[key].attr1 = propertylist.attr1;
                this.data[key].attr2 = propertylist.attr2;
                this.data[key].attr3 = propertylist.attr3;
              });
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
.editable-row-operations a {
  margin-right: 8px;
}
</style>
