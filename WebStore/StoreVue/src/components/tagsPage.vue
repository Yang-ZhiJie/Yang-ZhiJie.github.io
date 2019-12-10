<template>
  <a-table :columns="columns" :dataSource="data" bordered rowKey="id" v-if="isRouterAlive">
    <template v-for="col in ['value']" :slot="col" slot-scope="text, record, index">
      <div :key="col">
        <a-input
          v-if="record.editable&&record.tagId!=3"
          style="margin:-5px 0"
          :value="text"
          @change="e => handleChange(e.target.value, record.id, col)"
        />
        <img
          v-else-if="record.tagId==3"
          :src="url+'/'+record.value"
          style="width:50px;heigth:40px;margin-right:2px"
        />
        <template v-else>{{text}}</template>
      </div>
    </template>
    <template slot="updateSku" slot-scope="text, record, index">
      <div class="editable-row-operations">
        <!-- <span v-if="record.editable">
          <a @click="() => save(record.id)">修改</a>
          <a-popconfirm title="确认取消?" @confirm="() => cancel(record.id)">
            <a>取消</a>
          </a-popconfirm>
        </span>-->
        <span>
          <a @click="() => edit(record.id)">修改</a>
        </span>
        <!-- <span v-if="record.editable">
          <a @click="showmoal">修改</a>
        </span>-->
      </div>
      <div v-if="show">
        <!-- <a-button type="primary" @click="showModal">Open Modal with customized footer</a-button> -->
        <a-modal v-model="visible" title="修改标签" onOk="handleOk">
          <template slot="footer">
            <a-button key="back" @click="handleCancel">取消</a-button>
            <a-button type="primary" :loading="loading" @click="handleOk">提交</a-button>
          </template>
          <a-form @submit="updateData" :form="form" class="update_tag">
            <a-form-item v-if="valueList.tagId==2">
              <a-input
                v-decorator="[
          'value',
          { rules: [{ required: true, message: '输入促销宣传语' }] },
        ]"
                type="text"
                :placeholder="valueList.value"
              ></a-input>
            </a-form-item>
            <a-form-item>
              <a-button v-if="valueList.tagId==2" html-type="submit" type="primary">验证促销语句是否合法</a-button>
            </a-form-item>
          </a-form>
          <a-form-item v-if="valueList.tagId==3">
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
            <!-- <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>-->

            <img
              :src="url+'/'+valueList.value"
              alt
              style="width:150px;heigth:140px;margin-right:2px"
            />
          </a-form-item>
          <a-form-item v-else-if="valueList.tagId==1">
            <!-- 日期上传 -->
            <a-range-picker
              :showTime="{ format: 'HH:mm' }"
              format="YYYY-MM-DD"
              :placeholder="['生产日期', '过期时间']"
              @change="ontimeChange"
              style="margin-top:30px;"
            />
          </a-form-item>
        </a-modal>
      </div>
    </template>

    <template slot="deleteSku" slot-scope="text, record,index">
      <a-popconfirm title="Sure to delete?" @confirm="() => onDelete(record.id)">
        <a href="javascript:;">删除标签</a>
      </a-popconfirm>
    </template>
  </a-table>
</template>


<script>
import { get } from "http";
const columns = [
  {
    title: "标签值",
    dataIndex: "value",
    // width: "25%",
    scopedSlots: { customRender: "value" }
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
import qs from "qs";

for (let i = 0; i < data.length; i++) {
  data.push({
    // key: i.toString(),
    id: "",
    value: "",
    // productId:"",
    createdAt: "",
    updatedAt: ""
  });
}
export default {
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "update_tag" });
  },
  data() {
    this.cacheData = data.map(item => ({ ...item }));
    return {
      data,
      columns,
      token: "",
      show: false,
      loading: false,
      visible: false,
      valueList: "",
      previewVisible: false,
      previewImage: "",
      fileList: [],
      choseId: 0,
      isRouterAlive: true
    };
  },
  provide() {
    return {
      reload: this.reload
    };
  },
  mounted() {
    this.getToken();
  },
  methods: {
    //页面重加载
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function() {
        this.isRouterAlive = true;
      });
    },
    inputChange(e) {
      console.log(e.data);
    },
    //获取图像
    handlePreview(file) {
      console.log(file);
      this.previewImage = file.url;
      this.previewVisible = true;
    },
    handleCancel() {
      this.previewVisible = false;
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
            // console.log(this.fileList);
            this.fileList.push({
              uid: "0",
              status: "done",
              name: "img",
              url: "http://localhost:8080/" + this.Imgs
            });
            this.choseId = 3;
          }
        });
    },
    //获取时间
    ontimeChange(value, dateString) {
      // console.log("Selected Time: ", value);
      console.log("Formatted Selected Time: ", dateString);
      for (const key in dateString) {
        this.productiondate = dateString[0] + "~" + dateString[1];
      }
      console.log(this.productiondate);
      this.choseId = 1;
    },
    //获取input框的值
    updateData(e) {
      // console.log(e);
      e.preventDefault();
      this.form.validateFields((err, values) => {
        console.log(values);
        this.salesentance = values.value;
        this.choseId = 2;

        // this.upId = this.valueList.productId;
        // console.log(this.upId);
      });
    },
    showmodel() {
      this.show = true;
    },
    //删除操作
    onDelete(key) {
      console.log(key);
      let id = key;
      let token = this.token;
      let dataSource = [...this.data];
      this.data = dataSource.filter(item => item.id !== key);
      this.axios
        .post(this.url + `api/deltag/${id}?token=${token}`)
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
        this.valueList = target;
        console.log(this.valueList);
        this.uptagId = this.valueList.id;
        console.log(this.uptagId);
        target.editable = true;
        this.show = true;
        this.visible = true;
        this.data = newData;
      }
    },
    showModal() {
      this.visible = true;
    },
    handleOk(e) {
      console.log("1111");
      // console.log(this.choseId);
      this.loading = true;
      setTimeout(() => {
        this.visible = false;
        this.loading = false;
      }, 3000);
      switch (this.choseId) {
        case 1:
          let tagData = qs.stringify({
            id: this.uptagId,
            value: this.productiondate
          });
          this.axios
            .post(this.url + "api/updatetag?token=" + this.token, tagData)
            .then(res => {
              console.log(res);
              if (res.status == 200) {
                this.getToken();
              }
            });
          break;
        case 2:
          let tagData1 = qs.stringify({
            id: this.uptagId,

            value: this.salesentance
          });
          this.axios
            .post(this.url + "api/updatetag?token=" + this.token, tagData1)
            .then(res => {
              console.log(res);
              if (res.status == 200) {
                this.getToken();
              }
            });
          break;
        case 3:
          let tagData2 = qs.stringify({
            id: this.uptagId,

            value: this.Imgs
          });
          this.axios
            .post(this.url + "api/updatetag?token=" + this.token, tagData2)
            .then(res => {
              console.log(res);
              if (res.status == 200) {
                this.getToken();
              }
            });
          break;

        default:
          break;
      }
    },
    handleCancel(e) {
      this.visible = false;
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
      //   if (this.productId == undefined) {
      //     this.$notification.open({
      //       message: "数据加载失败",
      //       description: "请重新选择商品"
      //     });
      //     this.goback();
      //   }
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
            .get(this.url + "api/getByProId?id=" + id + "&token=" + token)
            .then(res => {
              console.log(res.data.data.data);
              if (res.data.status == true) {
                this.data = res.data.data.data;
              } else {
                alert("登录失效，请重新登录");
                this.$router.push("/");
              }
            });
        } else {
          this.axios.get(this.url + "api/allTags?token=" + token).then(res => {
            console.log(res.data.data.data);
            if (res.data.status == true) {
              this.data = res.data.data.data;
              // this.fileList.push({
              //   uid: "0",
              //   status: "done",
              //   name: "img",
              //   url: "http://localhost:8080/" + this.data.value
              // });
            } else {
              alert("服务器繁忙，请重新登录");
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
