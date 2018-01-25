package mx.diegop.architecturecomponents.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.diegop.architecturecomponents.R;
import mx.diegop.architecturecomponents.network.entities.Issue;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssueViewHolder> {

    private List<Issue> issues;

    public IssuesAdapter() {
        issues = new ArrayList<>();
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
        notifyDataSetChanged();
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_item, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {
        holder.bind(getIssue(position));
    }

    private Issue getIssue(int position) {
        return issues.get(position);
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public IssueViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.issue_text);
        }

        public void bind(Issue issue) {
            textView.setText(issue.title);
        }
    }
}
