import { QuestionCircleOutlined } from '@ant-design/icons';
import { Space } from 'antd';
import React from 'react';
import { SelectLang, useModel } from 'umi';
import Avatar from './AvatarDropdown';
import Notice from '@/components/NoticeIcon';
import styles from './index.less';

export type SiderTheme = 'light' | 'dark';

const GlobalHeaderRight: React.FC = () => {
  const { initialState } = useModel('@@initialState');

  if (!initialState || !initialState.settings) {
    return null;
  }

  const { navTheme, layout } = initialState.settings;
  let className = styles.right;

  if ((navTheme === 'dark' && layout === 'top') || layout === 'mix') {
    className = `${styles.right}  ${styles.dark}`;
  }
  return (
    <Space className={className}>
      <Notice/>
      <Avatar menu={true}/>
      <span
        className={styles.action}
        onClick={() => {
          window.open('https://github.com/On-Security/on-security/issues');
        }}
      >
        <QuestionCircleOutlined />
      </span>
      <SelectLang className={styles.action} />
    </Space>
  );
};
export default GlobalHeaderRight;
