//package com.swufe.library.activity;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Looper;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatDialogFragment;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.swufe.library.R;
//import com.swufe.library.bean.Reader;
//import com.swufe.library.bean.Result;
//import com.swufe.library.util.HttpUtil;
//import com.swufe.library.util.URLs;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class ForgetDialog extends AppCompatDialogFragment {
//    EditText edtTxt_forget_account, edtTxt_forget_telephone, edtEdtTxt_forget_pwd, edtTxt_forget_pwdConfirm;
//    Button btn_forget_reset, btn_forget_cancel;
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_forget, null);
//
//        edtTxt_forget_account = view.findViewById(R.id.edtTxt_forget_account);
//        edtTxt_forget_telephone = view.findViewById(R.id.edtTxt_forget_telephone);
//        edtEdtTxt_forget_pwd = view.findViewById(R.id.edtTxt_forget_password);
//        edtTxt_forget_pwdConfirm = view.findViewById(R.id.edtTxt_forget_passwordConfirm);
//
//        btn_forget_reset = view.findViewById(R.id.btn_forget_reset);
//        btn_forget_cancel = view.findViewById(R.id.btn_forget_cancel);
//
//        builder.setView(view);
//        builder.setTitle("忘记密码");
//
//
//        btn_forget_reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
//            }
//        });
//        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getContext(),"Click cancel",Toast.LENGTH_LONG).show();
//            }
//
//        });
//        return builder.create();
//    }
//}
