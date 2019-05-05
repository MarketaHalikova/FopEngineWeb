package de.irs.fopengine.fopengineweb.git;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.BranchTrackingStatus;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

/**
 * Auxiliary methods for git handling
 */
public class GitUtils {

    private GitUtils() {
    }

    /**
     * Create git object for git API
     *
     * @param gitDirectory git web url
     * @return git object for git API
     * @throws IOException directory doesn't contain correct git project
     */
    public static Git createGit(String gitDirectory) throws IOException {
        return Git.open( new File(gitDirectory));
    }


    /**
     * Method clones repository to new directory in directory from parameter.
     * The name of project director is the name of git repository
     * @param url git web url
     * @param directory directory to save project
     * @param credentials git remote repository creadentials
     * @return
     * @throws GitAPIException error by cloning git repository
     */
    public static Git cloneRepository(String url, String directory,
                                      UsernamePasswordCredentialsProvider credentials) throws GitAPIException {
        // clone the repo!
        boolean cloneAll = true;

        CloneCommand command = Git.cloneRepository();
        command.setCredentialsProvider(credentials).
                setCloneAllBranches(cloneAll)
                .setURI(url)
                .setDirectory(new File(directory));
        Git git = command.call();
        git.getRepository().close();
        git.close();
        return git;
    }


    /**
     * Return true if there aren't changes on local repository
     * @param git Git Api object
     * @return true if there aren't changes on local repository
     * @throws GitAPIException error by matching git repository
     */
    public static boolean localChanges(Git git) throws GitAPIException {
        return !git.status().call().isClean();
    }

    /**
     * Return actual branch
     * @param git
     * @return
     * @throws IOException
     */
    public static String getActualBranch(Git git) throws IOException {
        String actualBranch = git.getRepository().getFullBranch();
        return actualBranch.substring(actualBranch.lastIndexOf("/") + 1);
    }

    /**
     * Method add the changes to stage, commits changes and push it to the remote repository
     * @param author author of commit
     * @param email email of author
     * @param message commit message
     * @param pushOnCommit true if commit should be pushed to the remote directory
     * @return commit id
     * @throws GitAPIException  error in git
     */
    public static String addCommitPush(String author, String email, String message,
                                       Git git, UsernamePasswordCredentialsProvider credentials, boolean pushOnCommit)
            throws GitAPIException {

        git.add().addFilepattern(".").call();

        RevCommit revCommit;
        CommitCommand command = git.commit();
        command.setCommitter(author, email);
        command.setMessage(message);
        command.setAll(true);
        try {
            revCommit = command.call();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to commit", e);
        }
        if (pushOnCommit) {
            PushCommand push = git.push();
            push.setCredentialsProvider(credentials);
            push.call();
        }
        return revCommit.getId().getName();
    }

    /**
     * Update project if it is changed in remote and return true if project was successfully updated
     * @param git jGit Api Git object
     * @return true if project was successfully updated
     * @throws IOException
     * @throws GitAPIException an exception in git
     */
    public static boolean updateFromRemoteIfChanged(Git git, UsernamePasswordCredentialsProvider credentials) throws IOException, GitAPIException {
        if(GitUtils.isChanged(git, credentials)) {
            GitUtils.pull(git, credentials);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Pull changes from remote Git repository
     * @param git jGit Api Git object
     * @param credentials git repository credentials
     * @throws GitAPIException an exception in git
     */
    public static void pull(Git git, UsernamePasswordCredentialsProvider credentials) throws GitAPIException {
        git.pull().setCredentialsProvider(credentials).call();
    }

    /**
     * Method returns true if remote branch is changed
     * Method user fetch command to get changes from remote and compare local and
     * remote repository.
     * @param git jGit Api Git object
     * @param credentials git repository credentials
     * @return true if remote branch is changed
     * @throws IOException
     * @throws GitAPIException an exception in git
     */
    public static boolean isChanged(Git git, UsernamePasswordCredentialsProvider credentials) throws IOException, GitAPIException {
        GitUtils.fetch(git, credentials);
        BranchTrackingStatus trackingStatus = BranchTrackingStatus.of(git.getRepository(), "refs/heads/master");
        if (trackingStatus != null) {
            return trackingStatus.getAheadCount() - trackingStatus.getBehindCount() != 0;
        } else {
            throw new NoHeadException("Master nor found");
        }
    }

    /**
     * Fetch from master
     * @param git jGit Api Git object
     * @param credentials git repository credentials
     * @throws GitAPIException
     */
    public static void fetch(Git git, UsernamePasswordCredentialsProvider credentials) throws GitAPIException {
        FetchResult result = git
                .fetch()
                .setCredentialsProvider(credentials)
                .setDryRun(false)
                .call();
    }


    /**
     * CReate credentialsOptional for git repository
     * @param user git user name
     * @param password git password
     * @return
     */
    public static UsernamePasswordCredentialsProvider createCredentials(String user, String password) {
        return new UsernamePasswordCredentialsProvider( user, password );
    }
}
