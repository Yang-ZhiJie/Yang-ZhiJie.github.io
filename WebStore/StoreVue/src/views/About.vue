<template>
  <a-layout id="components-layout-demo-top-side">
    <a-layout-header class="header">
      <div class="logo">
        <span class="ufont">{{name}}</span>
      </div>
      <a-menu
        theme="dark"
        mode="horizontal"
        :defaultSelectedKeys="['1']"
        :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1">商品页</a-menu-item>
        <a-menu-item key="2">属性页</a-menu-item>
        <a-menu-item key="3">库存页</a-menu-item>
        <a-menu-item key="4">导航页</a-menu-item>
        <a-menu-item key="5">标签页</a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content style="padding: 0 50px">
      <a-breadcrumb style="margin: 16px 0">
        <a-breadcrumb-item>Home</a-breadcrumb-item>
        <a-breadcrumb-item>List</a-breadcrumb-item>
        <a-breadcrumb-item>App</a-breadcrumb-item>
      </a-breadcrumb>
      <a-layout style="padding: 24px 0; background: #fff">
        <a-layout-sider width="200" style="background: #fff">
          <a-menu
            mode="inline"
            :defaultSelectedKeys="['1']"
            :defaultOpenKeys="['sub1']"
            style="height: 100%"
          >
            <a-sub-menu key="sub1">
              <span slot="title">
                <a-icon type="switcher" />商品详情
              </span>
              <a-menu-item key="1" @click="showPage1">商品列表</a-menu-item>
              <a-menu-item key="2" @click="showPage2">删除商品</a-menu-item>
              <a-menu-item key="3" @click="showPage3">增加商品</a-menu-item>
              <!-- <a-menu-item key="4">option4</a-menu-item> -->
            </a-sub-menu>
            <a-sub-menu key="sub2">
              <span slot="title" @click="gotype">
                <a-icon type="switcher" />商品类别
              </span>
            </a-sub-menu>
            <a-sub-menu key="sub3">
              <span slot="title" @click="goskus">
                <a-icon type="switcher" />商品库存
              </span>
            </a-sub-menu>
            <a-sub-menu key="sub4">
              <span slot="title" @click="gonav">
                <a-icon type="switcher" />导航菜单
              </span>
            </a-sub-menu>
            <a-sub-menu key="sub5">
              <span slot="title" @click="gotags">
                <a-icon type="switcher" />标签详情
              </span>
            </a-sub-menu>
          </a-menu>
        </a-layout-sider>
        <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
          <ProductPage v-if="page1"></ProductPage>
          <ProductDel v-if="page2"></ProductDel>
          <ProductAdd3 v-if="page3"></ProductAdd3>
        </a-layout-content>
      </a-layout>
    </a-layout-content>
    <!-- <a-layout-footer style="text-align: center">商城后台管理</a-layout-footer> -->
  </a-layout>
</template>

<script>
import ProductPage from "@/components/productsPage";
import ProductDel from "@/components/productDel";
import ProductAdd3 from "@/components/productAdd3";
export default {
  components: {
    ProductPage,
    ProductDel,
    ProductAdd3
  },
  data() {
    return {
      page1: true,
      page2: false,
      page3: false,
      name: ""
    };
  },
  mounted() {
    this.getAdmin();
  },
  methods: {
    getAdmin() {
      this.name = localStorage.getItem("adminName");
      console.log(this.name);
    },
    showPage1() {
      this.page1 = true;
      this.page2 = false;
      this.page3 = false;
    },
    showPage2() {
      this.page1 = false;
      this.page3 = false;
      this.page2 = true;
    },
    showPage3() {
      this.page3 = true;
      this.page1 = false;
      this.page2 = false;
    },
    gotype() {
      this.$router.push("/type");
    },
    gonav() {
      this.$router.push("/nav");
      // this.$notification.open({
      //   message: "未选择商品目标链接",
      //   description: "请在商品列表页点击查看导航获得详情",
      //   onClick: () => {
      //     console.log("Notification Clicked!");
      //   }
      // });
    },
    gotags() {
      this.$router.push("/tags");
    },
    goskus() {
      this.$notification.open({
        message: "未选择商品",
        description: "请在商品列表页点击查看库存详情",
        onClick: () => {
          console.log("Notification Clicked!");
        }
      });
    }
  }
};
</script>

<style>
#components-layout-demo-top-side .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 28px 16px 0;
  float: left;
}
.ufont {
  position: absolute;
  /* margin: 0 auto; */
  color: #ffffff;
  margin-top: -15px;
  margin-left: 30px;
  /* font-weight: bold; */
  font-size: 16px;
}
</style>
