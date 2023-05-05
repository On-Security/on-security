import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from 'umi';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: 'On-Security',
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'On-Security',
          title: 'On-Security',
          href: 'https://on-security.cn',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/On-Security/on-security',
          blankTarget: true,
        },
        {
          key: 'ApiBoot',
          title: 'ApiBoot',
          href: 'https://apiboot.minbox.org',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
