<template>
  <a-table :columns="columns" :dataSource="data" bordered rowKey="id">
    <template
      v-for="col in ['positionId', 'titleName','picture', 'linkType']"
      :slot="col"
      slot-scope="text, record, index"
    >
      <div :key="col">
        <a-input
          v-if="record.editable&&col!='picture'"
          style="margin: -5px 0"
          :value="text"
          max="4"
          @change="e => handleChange(e.target.value, record.id, col)"
        />

        <img
          v-else-if="col=='picture'"
          :src="url+'/'+text"
          alt="cnm"
          style="width:50px;heigth:40px;margin-right:2px"
        />
        <template v-else>{{text}}</template>
      </div>
    </template>
    <template slot="updateNav" slot-scope="text, record, index">
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
    <template slot="deleteNav" slot-scope="text, record,index">
      <a-popconfirm title="Sure to delete?" @confirm="() => onDelete(record.id)">
        <a href="javascript:;">删除导航</a>
      </a-popconfirm>
    </template>
  </a-table>
</template>

<script>
import qs from "qs";
const columns = [
  {
    title: "导航位置",
    dataIndex: "positionId",
    // width: "25%",
    scopedSlots: { customRender: "positionId" }
  },
  {
    title: "导航名字",
    dataIndex: "titleName",
    // width: "15%",
    scopedSlots: { customRender: "titleName" }
  },
  {
    title: "导航图片",
    dataIndex: "picture",
    // width: "40%",
    scopedSlots: { customRender: "picture" }
  },
  {
    title: "链接类型",
    dataIndex: "linkType",
    // width: "40%",
    scopedSlots: { customRender: "linkType" }
  },
  {
    title: "链接目标",
    dataIndex: "linkTarget",
    // width: "40%",
    scopedSlots: { customRender: "linkTarget" }
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
    dataIndex: "updateNav",
    scopedSlots: { customRender: "updateNav" }
  },
  {
    title: "删除操作",
    dataIndex: "deleteNav",
    scopedSlots: { customRender: "deleteNav" }
  }
];
const data = [];
for (let i = 0; i < data.length; i++) {
  data.push({
    // key: i.toString(),
    id: "",
    positionId: "",
    titleName: "",
    picture: "",
    linkType: "",
    linkTarget: "",
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
          path: "tags",
          name: "tags",
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
          positionId: target.positionId,
          title: target.titleName,
          linkType: target.linkType
        };
        console.log(product);
        const token = this.token;
        this.axios
          .post(
            this.url + `api/updateNav?token=${token}`,
            qs.stringify(product)
          )
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
    //删除导航
    onDelete(key) {
      console.log(key);
      let id = key;
      let token = this.token;
      let dataSource = [...this.data];
      this.data = dataSource.filter(item => item.id !== key);
      this.axios
        .post(this.url + `api/delNav/${id}?token=${token}`)
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
    //判断有没有token
    getToken() {
      //判断有没有token
      let token = localStorage.getItem("token");
      if (token != null) {
        this.token = token;
        this.getLinkTarget();
        this.getNav(token);
      } else {
        alert("请先登录");
        this.$router.push("/");
      }
    },
    async getLinkTarget() {
      this.linkTargetId = this.$route.params.productId;
      //   console.log(this.productId);
      // if (this.linkTargetId == undefined) {
      //   this.$notification.open({
      //     message: "数据加载失败",
      //     description: "请重新选择商品"
      //   });
      //   this.goback();
      // }
    },
    goback() {
      this.$router.push("/about");
    },
    getNav(token) {
      let id = this.linkTargetId;
      if (token != null) {
        if (id != undefined) {
          this.axios
            .get(this.url + "api/getNavBy?linkTarget=" + id + "&token=" + token)
            .then(res => {
              console.log(res);
              console.log(res.data.data.data);
              if (res.data.status == false) {
                alert("登录失效，请重新登录");
                this.$router.push("/");
              } else {
                this.data = res.data.data.data;
              }
            });
        } else {
          // alert("未获得链接目标");
          // this.getLinkTarget();
          // this.$router.push("/about");
          this.axios
            .get(this.url + "api/getAllNavs?token=" + token)
            .then(res => {
              console.log(res.data.data.data);

              if (res.data.status == false) {
                alert("登录失效，请重新登录");
                this.$router.push("/");
              } else {
                this.data = res.data.data.data;
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
/* .editable-row-operations {
  display: inline-block;
} */
.editable-row-operations a {
  margin-right: 8px;
  /* margin: 0 auto; */
}
</style>
