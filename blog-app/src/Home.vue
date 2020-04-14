<template>
  <div id="home">
    <el-container>
    	
    	<base-header :activeIndex="activeIndex"></base-header>
		  
		  <router-view class="me-container" v-if="viewShow"/>
		  
			<base-footer v-show="footerShow"></base-footer>
		  
		</el-container>
		
  </div>
  
</template>

<script>
import BaseFooter from '@/components/BaseFooter'
import BaseHeader from '@/views/BaseHeader'

export default {
  name: 'Home',
  data (){
  	return {
  			activeIndex: '/',
  			footerShow:true,
        viewShow: true
  	}
  },
  provide() {
    return {
      reload: this.reload
    };
  },
  methods: {
    reload() {
      this.viewShow = false;
      this.$nextTick(() => {
        this.viewShow = true;
      });
    }
  },
  components:{
  	'base-header':BaseHeader,
  	'base-footer':BaseFooter
  },
  beforeRouteEnter (to, from, next){
  	 next(vm => {
    	vm.activeIndex = to.path
  	})
  },
  beforeRouteUpdate (to, from, next) {
	  if(to.path == '/'){
	  	this.footerShow = true
	  }else{
	  	this.footerShow = false
	  }
	  this.activeIndex = to.path
	  next()
	}
}
</script>

<style>

#home{
  background-image: url("assets/background/bg5.jpg");
  background-repeat: repeat;
  /*min-height: 624px;*/
  /*position: absolute;*/
  top:0;
  left: 0;
  bottom: 0;
  right: 0;
}

.me-container{
  margin: 100px auto 140px;
}
</style>
