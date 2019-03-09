package pers.zjc.sams.module.personinfo.view;
import java.util.Date;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.StringUtils;
import com.zp.android.zlib.utils.TimeUtils;
import com.zp.android.zlib.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
import pers.zjc.sams.module.personinfo.DaggerPersonInfoComponent;
import pers.zjc.sams.module.personinfo.PersonInfoModule;
import pers.zjc.sams.module.personinfo.contract.PersonInfoContract;
import pers.zjc.sams.module.personinfo.presenter.PersonInfoPresenter;

public class PersonInfoFragment extends BaseFragment implements PersonInfoContract.View, View.OnClickListener {

    @Inject
    PersonInfoPresenter presenter;

    @Inject
    AppConfig appConfig;

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right)
    TextView barRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lbl_org)
    TextView lblOrg;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.lbl_acoount)
    TextView lblAcoount;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.lbl_device_no)
    TextView lblDeviceNo;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.lbl_sex)
    TextView lblSex;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.lbl_major)
    TextView lblMajor;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.lbl_email)
    TextView lblEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.lbl_tel)
    TextView lblTel;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.rl_tel)
    RelativeLayout rlTel;
    @BindView(R.id.lbl_idcard)
    TextView lblIdcard;
    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    @BindView(R.id.rl_idcard)
    RelativeLayout rlIdcard;
    @BindView(R.id.lbl_birth)
    TextView lblBirth;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.rl_birth)
    RelativeLayout rlBirth;
    @BindView(R.id.lbl_face)
    TextView lblFace;
    @BindView(R.id.txt_face)
    TextView txtFace;
    @BindView(R.id.rl_face)
    RelativeLayout rlFace;
    Unbinder unbinder;
    private TimePickerView pvTime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personinfo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPersonInfoComponent.builder().appComponent(SamsApplication.getComponent())
                .personInfoModule(new PersonInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        presenter.loadData();
    }

    private void initView() {
        barTitle.setText("个人信息");
        barRight.setText("提交");
        barRight.setVisibility(View.VISIBLE);
        barRight.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        initTimePicker();
        switch (appConfig.getRole()) {
            case "0":
                break;
            case "1":
                tvRole.setText("学生");
                etAccount.setText(appConfig.getAccount());
                etUserName.setText(appConfig.getUserName());
                tvMajor.setText(appConfig.getMajor());
                etTel.setText(appConfig.getTel());
                tvSex.setText(appConfig.getSex());
                rlBirth.setOnClickListener(this);
                break;
            case "2":
                tvRole.setText("教师");
                etAccount.setText(appConfig.getAccount());
                etUserName.setText(appConfig.getUserName());
                tvMajor.setText(appConfig.getMajor());
                etTel.setText(appConfig.getTel());
                tvSex.setText(appConfig.getSex());
                rlIdcard.setVisibility(View.GONE);
                rlBirth.setVisibility(View.GONE);
                rlFace.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void initTimePicker() {
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.d("onTimeSelect: ", TimeUtils.date2String(date, Const.DateFormat.WITHOUT_HMS));
                tvBirth.setText(TimeUtils.date2String(date, Const.DateFormat.WITHOUT_HMS));
            }
        }).setType(new boolean[]{true, true, true, false, false, false}) //显示年/月/日/时/分/秒
                .build();
    }

    public void back() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
    }


    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void fillTeacherData(Teacher data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etUserName.setText(data.getName());
                etEmail.setText(data.getEmail() == null ? "未录入" : data.getEmail());
            }
        });

    }

    @Override
    public void fillStuData(Student data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etUserName.setText(data.getName());
                etEmail.setText(data.getEmail() == null ? "未录入" : data.getEmail());
                tvIdcard.setText(data.getIdCard() == null ? "未录入": data.getIdCard());
                tvBirth.setText(data.getBirthday() == null ? "2000-01-01" : TimeUtils.date2String(data.getBirthday(), Const.DateFormat.WITHOUT_HMS));
            }
        });

    }

    @Override
    public void showNetWorkErro() {
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_right:
                String userName = etUserName.getText().toString();
                String account = etAccount.getText().toString();
                String email = etEmail.getText().toString();
                String tel = etTel.getText().toString();
                if (StringUtils.isEmpty(userName)) {
                    showShortToast("用户名不能为空");
                    return;
                }
                if (StringUtils.isEmpty(account)) {
                    showShortToast("账号不能为空");
                    return;
                }
                switch (appConfig.getRole()) {
                    case "0":
                        break;
                    case "1":
                        Student student = new Student();
                        student.setStuId(Integer.valueOf(appConfig.getUserId()));
                        String birth = tvBirth.getText().toString();
                        if (StringUtils.isEmpty(birth)) {
                            birth = "2000-01-01";
                        }
                        student.setBirthday(TimeUtils.string2Date(birth, Const.DateFormat.WITHOUT_HMS));
                        student.setName(userName);
                        student.setEmail(email);
                        student.setTel(tel);
                        presenter.commit(student);
                        break;
                    case "2":
                        Teacher teacher = new Teacher();
                        teacher.setId(Integer.valueOf(appConfig.getUserId()));
                        Log.d("name", userName);
                        teacher.setName(userName);
                        teacher.setEmail(email);
                        teacher.setTel(tel);
                        presenter.commit(teacher);
                        break;
                    default:
                        break;
                }

                break;
            case R.id.rl_birth:
                pvTime.show();
                break;
            default:
                break;
        }
    }
}
