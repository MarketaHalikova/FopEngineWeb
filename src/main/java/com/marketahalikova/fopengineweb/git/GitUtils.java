package com.marketahalikova.fopengineweb.git;

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

public class GitUtils {

    private GitUtils() {
    }

    /**
     * Create git object for git API
     *
     * @param gitDirectory
     * @return
     * @throws IOException
     */
    public static Git createGit(String gitDirectory) throws IOException {

        Git git = Git.open( new File(gitDirectory));
        return git;
    }


    public static Git cloneRepository(String url, String directory, UsernamePasswordCredentialsProvider credentials) throws GitAPIException {

        // clone the repo!
        boolean cloneAll = true;

        CloneCommand command = Git.cloneRepository();
        command.setCredentialsProvider(credentials).
                setCloneAllBranches(cloneAll)
                .setURI(url)
                .setDirectory(new File(directory));
        return command.call();

    }


    /**
     * Return true if there aren't changes on local repository
     * @param git
     * @return
     * @throws GitAPIException
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
     *
     * @param author
     * @param email
     * @param message
     * @param pushOnCommit
     * @return
     * @throws GitAPIException
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
     * Update project if it is changed in remote and return true if project wasupdated
     * @param git
     * @return
     * @throws IOException
     * @throws GitAPIException
     */
    public static boolean updateFromRemoteIfChanged(Git git, UsernamePasswordCredentialsProvider credentials) throws IOException, GitAPIException {
        if(GitUtils.isChanged(git, credentials)) {
            GitUtils.pull(git, credentials);
            return true;
        } else {
            return false;
        }
    }

    public static void pull(Git git, UsernamePasswordCredentialsProvider credentials) throws GitAPIException {
        git.pull().setCredentialsProvider(credentials).call();
    }

    /**
     * Method returns true if remote branch is changed
     * Method user fetch command to get changes from remote and compare local and
     * remote repository.
     * @param git
     * @param credentials
     * @return
     * @throws IOException
     * @throws GitAPIException
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
     * @param git
     * @param credentials
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
     * @param user
     * @param password
     * @return
     */
    public static UsernamePasswordCredentialsProvider createCredentials(String user, String password) {
        return new UsernamePasswordCredentialsProvider( user, password );
    }
}
