package com.example.danilo.appdebts.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.danilo.appdebts.DAO.DebtDAO;
import com.example.danilo.appdebts.R;
import com.example.danilo.appdebts.classes.Debt;
import com.example.danilo.appdebts.database.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DebtsAdapter extends RecyclerView.Adapter<DebtsAdapter.ViewHolderDebts> {
    private List<Debt> mData;
    private List<ViewHolderDebts> mDataViews = new ArrayList<ViewHolderDebts>();
    private int selectedItem = -1;
    private int actualItem = -1;

    private DebtDAO mDebtDAO;

    public DebtsAdapter(List<Debt> data) {
        mData = data;
    }

    private Context mContext;

    @NonNull
    @Override
    public DebtsAdapter.ViewHolderDebts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_view_debts, parent, false);
        ViewHolderDebts holderDebts = new ViewHolderDebts(view);
        mDataViews.add(holderDebts);
        return holderDebts;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDebts holder, int position) {
        if (mData != null && mData.size() > 0) {
            Debt debt = mData.get(position);
            holder.mDescription.setText(debt.getDescription());
            holder.mCategory.setText(debt.getCategory().getType());
            holder.mPay.setText(debt.getPayDate());
            holder.mPayment.setText(debt.getPaymentDate());
            holder.mValue.setText(formatMoney(debt.getValue()));
        }
    }

    private void updateViewHolderLast() {
        ViewHolderDebts holder = mDataViews.get(selectedItem);
        holder.mbtnEdit.setVisibility(View.GONE);
        holder.mbtnDelete.setVisibility(View.GONE);
        holder.mbtnPay.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderDebts extends RecyclerView.ViewHolder {
        public TextView mDescription;
        public TextView mCategory;
        public TextView mPay;
        public TextView mPayment;
        public TextView mValue;
        public TextView mStatus;
        public ImageButton mbtnEdit;
        public ImageButton mbtnDelete;
        public ImageButton mbtnPay;
        public ConstraintLayout mlayoutDebt;

        public ViewHolderDebts(View itemView) {
            super(itemView);
            mlayoutDebt = itemView.findViewById(R.id.layoutDebt);
            mDescription = itemView.findViewById(R.id.txtViewDescription);
            mCategory = itemView.findViewById(R.id.txtViewCategory);
            mPay = itemView.findViewById(R.id.txtViewPay);
            mPayment = itemView.findViewById(R.id.txtViewPayment);
            mValue = itemView.findViewById(R.id.txtViewValue);
            mStatus = itemView.findViewById(R.id.txtViewStatus);
            mbtnEdit = itemView.findViewById(R.id.btnEdit);
            mbtnDelete = itemView.findViewById(R.id.btnDelete);
            mbtnPay = itemView.findViewById(R.id.btnPay);


            mbtnEdit.setVisibility(View.GONE);
            mbtnDelete.setVisibility(View.GONE);
            mbtnPay.setVisibility(View.GONE);

            mlayoutDebt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actualItem = getLayoutPosition();
                    if (selectedItem != -1 && actualItem != selectedItem) {
                        updateViewHolderLast();
                    }

                    if (mbtnPay.getVisibility() == View.GONE) {
                        mbtnEdit.setVisibility(View.VISIBLE);
                        mbtnDelete.setVisibility(View.VISIBLE);
                        mbtnPay.setVisibility(View.VISIBLE);
                    } else {
                        mbtnEdit.setVisibility(View.GONE);
                        mbtnDelete.setVisibility(View.GONE);
                        mbtnPay.setVisibility(View.GONE);
                    }

                    selectedItem = actualItem;
                }
            });

            mbtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mData.size() > 0) {
                        int debtPosition = getLayoutPosition();
                        Debt debt = mData.get(debtPosition);
                        SQLiteDatabase connection = DBConnection.getConnection(mContext);
                        mDebtDAO = new DebtDAO(connection);
                        mDebtDAO.remove(debt.getId());
                        mData.remove(debtPosition);
                        notifyItemRemoved(debtPosition);
                    }
                }
            });
        }
    }

    private String formatMoney(double value) {
        return String.format(Locale.getDefault(), "R$ %.2f", value);
//        return "R$ " + String.valueOf(value).replace(".", ",");
    }
}