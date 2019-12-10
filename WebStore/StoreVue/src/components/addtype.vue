<template>
  <div>
    <a-modal
      :destroyOnClose="true"
      :visible="visible"
      width="1000px"
      :closable="false"
      okText="确定"
      cancelText="取消"
      centered
      :confirmLoading="btnLoading"
      @ok="save"
      @cancel="visible=false"
      title="商品规格添加"
    >
      <div style="overflow-y: scroll;height: 400px">
        <a-form layout="inline" :form="form" style="margin-bottom: 15px">
          <a-row style="display: flex;flex-direction: row">
            <div v-if="rule">
              <span style="margin-right: 10px">规则名称：</span>

              <a-input
                placeholder="填写规格名称"
                v-for="(item,index) in ruledata"
                :key="index"
                v-model="item.rule_name"
                @blur="k_addrule(item,$event)"
                style="width: 150px;height: 40px;margin-right: 10px"
              >
                <a-icon
                  slot="suffix"
                  type="close-circle"
                  @click="deletrule(index)"
                  v-if="item.rule_name!=''"
                />
              </a-input>
            </div>

            <a-button
              type="dashed"
              icon="plus"
              @click="addRule"
              style="width: 150px;height: 40px"
            >添加新规则</a-button>
          </a-row>

          <a-row
            v-for="(item,index) in k_ruledata"
            :key="index"
            style="display: flex;flex-direction: row;margin-top: 20px;align-items: center;"
          >
            <div style="float:left;display: flex;flex-direction: row">
              <span style="margin-right: 10px;margin-top: 5px">{{item.k_rule_name}}:</span>

              <div
                v-if="item.k_rule"
                v-for="(itemto,indexto) in item.k_moreruledata"
                :key="indexto"
                style="margin-right: 10px;width: 80px;height: 30px;
                            display: flex;flex-direction: row;
                            color:#578CE5 ;
                            border-radius: 4px;border: 1px solid #578CE5"
              >
                <div
                  style="flex: 2;border-right: 1px solid #578CE5;text-align: center;line-height: 30px "
                >{{itemto.rule_name}}</div>
                <div style="flex: 1;text-align: center;line-height: 30px">X</div>
              </div>
            </div>

            <a-input
              placeholder="设置属性"
              v-model="item.provaluse"
              style="width: 150px;height: 30px;margin-right: 10px"
            />

            <a-button type="primary" @click="k_addpro(item)" style="width: 60px;height: 30px">添加</a-button>
          </a-row>

          <a-button type="primary" style="margin-top: 20px;width: 600px" @click="autodata">生成</a-button>

          <!--自动生成的数据列表-->

          <div v-if="autodata_rule">
            <a-row style="margin-top: 20px" v-for="(item,index) in autoruledata" :key="index">
              <a-col :span="3" style="text-align: center;">
                <div style="margin-top: 10px">{{item.rule_name}}</div>
              </a-col>

              <a-col :span="4">
                <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 12 }" label="金额">
                  <a-input-number
                    v-decorator="[
          'goods_cost',
          {rules: [{ required: true, message: '请填写金额' }]}
        ]"
                    :min="0"
                  ></a-input-number>
                </a-form-item>
              </a-col>
              <a-col :span="4">
                <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 12 }" label="库存">
                  <a-input-number
                    v-decorator="[
          'goods_cost',
          {rules: [{ required: true, message: '请填写库存' }]}
        ]"
                    :min="0"
                  ></a-input-number>
                </a-form-item>
              </a-col>
              <a-col :span="4">
                <a-form-item :label-col="{ span: 10 }" :wrapper-col="{ span: 12 }" label="成本价">
                  <a-input-number
                    v-decorator="[
          'goods_cost',
          {rules: [{ required: true, message: '请填写成本价' }]}
        ]"
                    :min="0"
                  ></a-input-number>
                </a-form-item>
              </a-col>
            </a-row>
          </div>
        </a-form>
      </div>
    </a-modal>

    <a-icon type="plus" style="font-size:20px;color:#3E86ED;margin-left: 10px" @click="toadd"></a-icon>
  </div>
</template>
 
<script>
export default {
  props: {
    ugro_id: {
      //订单ID
      type: Number,
      default: 0
    }
  },
  name: "",
  data() {
    return {
      visible: false,
      btnLoading: false,
      form: this.$form.createForm(this),
      rule: false, //规格
      ruledata: [],
      k_ruledata: [], //一个规格--》对应 多个属性
      autodata_rule: false,
      autoruledata: [], //自动生成的数据

      zum: []
    };
  },
  mounted() {},

  methods: {
    toadd() {
      //添加规格

      this.visible = true;
      this.rule = false;
      this.autodata_rule = false;

      this.ruledata = []; //暂时
      this.k_ruledata = [];
      this.autoruledata = [];
      this.zum = [];
    },

    //添加规则
    addRule() {
      //添加一个数据
      let ss = { rule_id: "", rule_name: "" };
      this.ruledata.push(ss);

      this.rule = true;
    },

    //删除规则
    deletrule(index) {
      this.$confirm({
        content: "确认删除这个规则名称?",
        okText: "确定",
        title: "提示",
        okType: "danger",
        centered: true,
        cancelText: "取消",
        onOk: () => {
          this.ruledata.splice(index, 1);
          //对应删除--相应的属性
          k_ruledata.split(index, 1);
        }
      });
    },

    k_addrule(item, event) {
      console.log(item);
      let ss = {
        k_rule_id: item.rule_id,
        k_rule_name: item.rule_name,
        k_rule: false,
        k_provaluse: "", //属性
        k_moreruledata: []
      };
      this.k_ruledata.push(ss);
      console.log(this.k_ruledata);
    },

    k_addpro(item) {
      //添加属性

      let ss = { rule_id: "", rule_name: item.provaluse };
      item.k_moreruledata.push(ss);
      item.k_rule = true;
      //添加完成后
      item.provaluse = "";
    },
    autodata() {
      //自动生成

      //autoruledata

      if (this.k_ruledata.length < 0) {
        this.$message.info("请先添加");
        return;
      }

      this.zum = [];

      for (let index in this.k_ruledata) {
        //循环的次数

        this.getDate(this.zum, this.k_ruledata[index]);
      }

      for (let index in this.zum) {
        this.zum[index].rule_id = "";
        this.zum[index].rule_cost = ""; //金额
        this.zum[index].rule_unit = ""; //库存
        this.zum[index].rule_price = ""; //成本价
      }
      this.autoruledata = this.zum;

      console.log(this.autoruledata);

      this.autodata_rule = true;
    },

    getDate(zum, first) {
      //公共存的集合  第一个集合
      console.log(this.zum);
      if (zum.length != 0) {
        let item = first.k_moreruledata;
        console.log(item);
        let zumto = [];

        for (let index in zum) {
          for (let to in item) {
            let ss = {};

            ss.rule_name =
              zum[index].rule_name +
              "/" +
              first.k_rule_name +
              ":" +
              item[to].rule_name;

            zumto.push(ss);
          }
        }

        this.zum = zumto;
        // console.log(this.zum);
      } else {
        let item = first.k_moreruledata;

        for (let index in item) {
          let ss = {};
          ss.rule_name = first.k_rule_name + ":" + item[index].rule_name;
          zum.push(ss);
        }

        this.zum = zum;
        // console.log(zum);
      }
    },

    save() {
      //   console.log("aaa");
    }
  }
};
</script>
 
<style lang="scss" scoped>
</style>