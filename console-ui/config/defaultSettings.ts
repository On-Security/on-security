import { Settings as LayoutSettings } from '@ant-design/pro-components';

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  primaryColor: '#1890ff',
  layout: 'mix',
  contentWidth: 'Fluid',
  // 左侧菜单宽度
  siderWidth: 256,
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: 'On-Security Console',
  pwa: false,
  logo: './logo-white.png',
  iconfontUrl: '',
};

export default Settings;
