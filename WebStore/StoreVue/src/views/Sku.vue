<template>
  <a-layout id="components-layout-demo-top-side">
    <a-layout-header class="header">
      <div class="logo" @click="gobackto">
        <span class="backfont">返回上层</span>
      </div>
      <a-menu
        theme="dark"
        mode="horizontal"
        :defaultSelectedKeys="['3']"
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
            :defaultOpenKeys="['sub3']"
            style="height: 100%"
          >
            <a-sub-menu key="sub1">
              <span slot="title" @click="goproduct">
                <a-icon type="switcher" />商品详情
              </span>
            </a-sub-menu>
            <a-sub-menu key="sub2" @click="gotype">
              <span slot="title">
                <a-icon type="switcher" />商品类别
              </span>
            </a-sub-menu>
            <a-sub-menu key="sub3">
              <span slot="title">
                <a-icon type="switcher" />商品库存
              </span>
              <a-menu-item key="1" @click="showPage1">库存列表</a-menu-item>
              <a-menu-item key="2" @click="showPage2">增加库存</a-menu-item>
              <!-- <a-menu-item key="2" @click="showPage2">删除库存</a-menu-item> -->
            </a-sub-menu>
            <a-sub-menu key="sub4" @click="gonav">
              <span slot="title">
                <a-icon type="switcher" />导航菜单
              </span>
            </a-sub-menu>
            <a-sub-menu key="sub5" @click="gotags">
              <span slot="title">
                <a-icon type="switcher" />标签详情
              </span>
            </a-sub-menu>
          </a-menu>
        </a-layout-sider>
        <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
          <SkuPage v-if="page1"></SkuPage>
          <SkuAdd v-if="page2"></SkuAdd>
          <!-- <TypeDel v-if="page3"></TypeDel> -->
        </a-layout-content>
      </a-layout>
    </a-layout-content>
    <a-layout-footer style="text-align: center">商城后台管理</a-layout-footer>
  </a-layout>
</template>

<script>
import SkuPage from "@/components/skuPage";
// import TypeDel from "@/components/typeDel";
import SkuAdd from "@/components/skuAdd";
export default {
  components: {
    SkuPage,
    // TypeDel,
    SkuAdd
  },
  data() {
    return {
      page1: true,
      page2: false
      // page3: false
    };
  },
  methods: {
    showPage1() {
      this.page1 = true;
      this.page2 = false;
    },
    showPage2() {
      this.page1 = false;
      this.page2 = true;
    },
    goproduct() {
      this.$router.push("/about");
    },
    gotype() {
      this.$router.push("/type");
    },
    gonav() {
      this.$router.push("/nav");
    },
    gotags() {
      this.$router.push("/tags");
    },
    gobackto() {
      this.$router.go(-1);
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
.backfont {
  position: absolute;
  /* margin: 0 auto; */
  color: #ffffff;
  margin-top: -15px;
  margin-left: 30px;
  /* font-weight: bold; */
  font-size: 16px;
}
</style>
